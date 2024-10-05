package com.tfkfan.bot.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SearchQueryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SearchQuery getSearchQuerySample1() {
        SearchQuery searchQuery = new SearchQuery();
        searchQuery.id = 1L;
        searchQuery.value = "value1";
        searchQuery.minPrice = 1L;
        searchQuery.maxPrice = 1L;
        return searchQuery;
    }

    public static SearchQuery getSearchQuerySample2() {
        SearchQuery searchQuery = new SearchQuery();
        searchQuery.id = 2L;
        searchQuery.value = "value2";
        searchQuery.minPrice = 2L;
        searchQuery.maxPrice = 2L;
        return searchQuery;
    }

    public static SearchQuery getSearchQueryRandomSampleGenerator() {
        SearchQuery searchQuery = new SearchQuery();
        searchQuery.id = longCount.incrementAndGet();
        searchQuery.value = UUID.randomUUID().toString();
        searchQuery.minPrice = longCount.incrementAndGet();
        searchQuery.maxPrice = longCount.incrementAndGet();
        return searchQuery;
    }
}
