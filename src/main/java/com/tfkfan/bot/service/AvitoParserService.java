package com.tfkfan.bot.service;

import com.tfkfan.bot.domain.SearchItem;
import com.tfkfan.bot.domain.SearchItemTmp;
import com.tfkfan.bot.domain.SearchQuery;
import com.tfkfan.bot.telegram.TelegramBot;
import io.github.bonigarcia.wdm.WebDriverManager;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class AvitoParserService {

    private static final Integer TIMEOUT_SECONDS = 5;
    private static final String SUFFIX_PATTERN_TO_REMOVE = "(\\?context=.*)|(\\?slocation=.*)";

    private final Logger log = LoggerFactory.getLogger(AvitoParserService.class);

    @ConfigProperty(name = "avito.max-page")
    Integer maxPage;

    @ConfigProperty(name = "avito.base")
    String baseUrl;

    @ConfigProperty(name = "avito.locations")
    List<String> locations;

    @ConfigProperty(name = "avito.category")
    String category;

    private final EntityManager entityManager;
    private final TelegramBot bot;

    public AvitoParserService(EntityManager entityManager, TelegramBot bot) {
        this.entityManager = entityManager;
        this.bot = bot;
    }

    public void run() {
        final List<SearchQuery> searchQueries = SearchQuery.findActive();

        execute(
            this::beforeJob,
            driver -> {
                for (var query : searchQueries) {
                    final String search = query.value;
                    final Long minPrice = query.minPrice;
                    final Long maxPrice = query.maxPrice;

                    get(driver, baseUrl);
                    inputValue(driver, search, "search-form/suggest/input", false);
                    submit(driver, "search-form/submit-button");

                    final String catUrl = getCurrentUrl(driver);
                    log.info(catUrl);

                    boolean trySubmit = false;

                    if (minPrice != null) {
                        inputValue(driver, minPrice, "price-from/input", true);
                        trySubmit = true;
                    }

                    if (maxPrice != null) {
                        inputValue(driver, maxPrice, "price-to/input", true);
                        trySubmit = true;
                    }

                    if (trySubmit) submit(driver, "search-filters/submit-button");

                    for (int p = 1; p <= maxPage; p++) {
                        final String pageUrl = catUrl + "&p=" + p;
                        get(driver, pageUrl);

                        log.info("page {}", p);
                        final List<WebElement> elems = driver.findElements(new By.ByCssSelector("div[data-marker='item']"));
                        if (elems.isEmpty()) {
                            log.warn("Elems is empty. Url: {}", pageUrl);
                            break;
                        }

                        for (WebElement e : elems) {
                            new Actions(driver).scrollToElement(e).perform();
                            WebElement anchor = e.findElement(new By.ByCssSelector("a[data-marker='item-title']"));

                            try {
                                final SearchItemTmp item = new SearchItemTmp();
                                item.href = Objects.requireNonNull(anchor.getAttribute("href")).replaceAll(SUFFIX_PATTERN_TO_REMOVE, "");

                                if (
                                    !item.href.contains(category) || locations.stream().noneMatch(location -> item.href.contains(location))
                                ) continue;

                                item.title = anchor.getAttribute("title");
                                item.img = getImageSrc(e);
                                item.price = Long.valueOf(
                                    Objects.requireNonNull(
                                        e.findElement(new By.ByCssSelector("meta[itemprop='price']")).getAttribute("content")
                                    )
                                );
                                processItem(item);
                            } catch (Exception ex) {
                                log.warn(ex.getMessage());
                            }
                        }
                    }
                }
            },
            this::afterJob
        );
    }

    @Transactional
    public void beforeJob() {
        SearchItemTmp.deleteAll();
    }

    @Transactional
    public void afterJob() {
        SearchItem.deleteAll();
        entityManager.createNativeQuery("INSERT INTO search_item SELECT * FROM search_item_tmp").executeUpdate();
    }

    @Transactional
    public void processItem(SearchItemTmp item) {
        if (SearchItem.findByIdOptional(item.href).isEmpty()) {
            notifyTg(item);
            item.persist();
        }
    }

    private void notifyTg(SearchItemTmp item) {
        try {
            bot.sendBroadcastNotification(item);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void execute(Runnable initCallback, Consumer<WebDriver> callback, Runnable finishCallback) {
        log.info("Parser started");
        initCallback.run();

        WebDriverManager.chromedriver().browserVersion("129.0.6668.100").setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*", "--no-sandbox", "--remote-debugging-port=9222"); //"--headless"
        WebDriver driver = null;

        boolean isError = false;
        try {
            driver = new ChromeDriver(options);
            driver.manage().timeouts().pageLoadTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS);
            callback.accept(driver);
        } catch (Exception e) {
            isError = true;
            throw e;
        } finally {
            if (driver != null) driver.close();
            if (!isError) finishCallback.run();
            log.info("Parser ended");
        }
    }

    private void get(WebDriver driver, String url) {
        try {
            driver.get(url);
        } catch (TimeoutException ignored) {}
    }

    private void submit(WebDriver driver, String dataMarker) {
        try {
            driver.findElement(By.cssSelector("button[data-marker='%s']".formatted(dataMarker))).click();
        } catch (TimeoutException ignored) {}
    }

    private void inputValue(WebDriver driver, Object value, String dataMarker, Boolean useLoop) {
        WebElement element = driver.findElement(By.cssSelector("input[data-marker='%s']".formatted(dataMarker)));
        element.clear();
        String str = value.toString();
        if (useLoop) {
            for (char c : str.toCharArray()) {
                String b = "" + c;
                element.sendKeys(b);
            }

            return;
        }
        element.sendKeys(str);
    }

    private String getCurrentUrl(WebDriver driver) {
        try {
            return driver.getCurrentUrl();
        } catch (TimeoutException ignored) {}
        return null;
    }

    private String getImageSrc(WebElement element) {
        try {
            return element.findElement(new By.ByCssSelector("img[class^='photo-slider-image']")).getAttribute("src");
        } catch (NoSuchElementException ignored) {}
        return null;
    }
}
