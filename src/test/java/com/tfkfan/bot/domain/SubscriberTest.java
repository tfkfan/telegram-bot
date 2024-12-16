package com.tfkfan.bot.domain;

import static com.tfkfan.bot.domain.SubscriberTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tfkfan.bot.TestUtil;
import org.junit.jupiter.api.Test;

class SubscriberTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subscriber.class);
        Subscriber subscriber1 = getSubscriberSample1();
        Subscriber subscriber2 = new Subscriber();
        assertThat(subscriber1).isNotEqualTo(subscriber2);

        subscriber2.id = subscriber1.id;
        assertThat(subscriber1).isEqualTo(subscriber2);

        subscriber2 = getSubscriberSample2();
        assertThat(subscriber1).isNotEqualTo(subscriber2);
    }
}
