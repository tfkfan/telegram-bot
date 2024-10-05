package com.tfkfan.bot.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class SubscriberTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Subscriber getSubscriberSample1() {
        Subscriber subscriber = new Subscriber();
        subscriber.id = 1L;
        return subscriber;
    }

    public static Subscriber getSubscriberSample2() {
        Subscriber subscriber = new Subscriber();
        subscriber.id = 2L;
        return subscriber;
    }

    public static Subscriber getSubscriberRandomSampleGenerator() {
        Subscriber subscriber = new Subscriber();
        subscriber.id = longCount.incrementAndGet();
        return subscriber;
    }
}
