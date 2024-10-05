package com.tfkfan.bot.telegram;

import com.tfkfan.bot.config.Constants;
import com.tfkfan.bot.domain.SearchItemTmp;
import com.tfkfan.bot.domain.Subscriber;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Startup
@Singleton
public class TelegramBot extends BaseCommandsHandler {

    TelegramBotsApi botsApplication;

    private final List<String> allowedUsers;

    @Inject
    public TelegramBot(
        @ConfigProperty(name = "telegram.token") String telegramToken,
        @ConfigProperty(name = "telegram.users") List<String> allowedUsers
    ) {
        super(telegramToken);
        this.allowedUsers = allowedUsers;
    }

    @PostConstruct
    public void init() throws TelegramApiException {
        log.info("Telegram bot registration");
        botsApplication = new TelegramBotsApi(DefaultBotSession.class);
        botsApplication.registerBot(this);
        log.info("Telegram bot registration complete");
    }

    public void sendNotification(final Subscriber subscriber, final SearchItemTmp item) {
        final SendMessage sendMsg = new SendMessage();
        final StringBuilder messageBuilder = new StringBuilder();
        sendMsg.setChatId(subscriber.id);

        messageBuilder.append(item.title);
        messageBuilder.append("\n");
        messageBuilder.append("%s рублей".formatted(item.price));
        messageBuilder.append("\n");
        messageBuilder.append("%s".formatted(item.href));

        sendMsg.setText(messageBuilder.toString());

        execute(sendMsg);
    }

    public void sendBroadcastNotification(final SearchItemTmp item) {
        Subscriber.findActive().forEach(s -> sendNotification(s, item));
    }

    @Override
    @Transactional
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage()) {
            return;
        }
        Message message = update.getMessage();

        if (!message.hasText()) {
            return;
        }

        final String command = message.getText();
        final SendMessage sendMsg = new SendMessage();
        final StringBuilder messageBuilder = new StringBuilder();
        final Long chatId = message.getChatId();
        sendMsg.setChatId(chatId);

        if (!allowedUsers.contains(message.getFrom().getUserName())) {
            log.info("Not authorized: {}", message.getFrom().getUserName());
            messageBuilder.append("You're not authorized to perform this command\n");
            sendMsg.setText(messageBuilder.toString());

            execute(sendMsg);
            return;
        }

        final Optional<Subscriber> sOpt = Subscriber.findByIdOptional(chatId);
        final boolean isNew = sOpt.isEmpty();
        final Subscriber subscriber = sOpt.orElse(new Subscriber(chatId));

        boolean persist = false;

        switch (command) {
            case Constants.START_COMMAND -> {
                subscriber.active = true;
                persist = true;
                messageBuilder.append("Started\n");
            }
            case Constants.STOP_COMMAND -> {
                subscriber.active = false;
                persist = true;
                messageBuilder.append("Stopped\n");
            }
            case Constants.HELP_COMMAND -> {
                messageBuilder.append("Help info\n");
            }
            default -> {
                messageBuilder.append("Unknown command\n");
            }
        }
        if (persist) {
            if (isNew) subscriber.persist();
            else subscriber.update();
        }

        sendMsg.setText(messageBuilder.toString());

        execute(sendMsg);
    }

    @Override
    public String getBotUsername() {
        return "fgsdfg";
    }
}
