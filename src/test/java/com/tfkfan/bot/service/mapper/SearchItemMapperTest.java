package com.tfkfan.bot.service.mapper;

import static com.tfkfan.bot.domain.SearchItemAsserts.*;
import static com.tfkfan.bot.domain.SearchItemTestSamples.*;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@QuarkusTest
class SearchItemMapperTest {

    @Inject
    SearchItemMapper searchItemMapper;

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSearchItemSample1();
        var actual = searchItemMapper.toEntity(searchItemMapper.toDto(expected));
        assertSearchItemAllPropertiesEquals(expected, actual);
    }
}
