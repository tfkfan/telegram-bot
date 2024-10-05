package com.tfkfan.bot.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SearchItemTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SearchItem getSearchItemSample1() {
        SearchItem searchItem = new SearchItem();
        searchItem.title = "title1";
        searchItem.img = "img1";
        searchItem.price = 1L;
        searchItem.href = "href1";
        return searchItem;
    }

    public static SearchItem getSearchItemSample2() {
        SearchItem searchItem = new SearchItem();
        searchItem.title = "title2";
        searchItem.img = "img2";
        searchItem.price = 2L;
        searchItem.href = "href2";
        return searchItem;
    }

    public static SearchItem getSearchItemRandomSampleGenerator() {
        SearchItem searchItem = new SearchItem();
        searchItem.title = UUID.randomUUID().toString();
        searchItem.img = UUID.randomUUID().toString();
        searchItem.price = longCount.incrementAndGet();
        searchItem.href = UUID.randomUUID().toString();
        return searchItem;
    }
}
