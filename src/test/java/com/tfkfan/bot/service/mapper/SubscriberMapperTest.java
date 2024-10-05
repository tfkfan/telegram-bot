package com.tfkfan.bot.service.mapper;

import static com.tfkfan.bot.domain.SubscriberAsserts.*;
import static com.tfkfan.bot.domain.SubscriberTestSamples.*;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@QuarkusTest
class SubscriberMapperTest {

    @Inject
    SubscriberMapper subscriberMapper;

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSubscriberSample1();
        var actual = subscriberMapper.toEntity(subscriberMapper.toDto(expected));
        assertSubscriberAllPropertiesEquals(expected, actual);
    }
}
