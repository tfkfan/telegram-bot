package com.tfkfan.bot.service.mapper;

import static com.tfkfan.bot.domain.SearchQueryAsserts.*;
import static com.tfkfan.bot.domain.SearchQueryTestSamples.*;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@QuarkusTest
class SearchQueryMapperTest {

    @Inject
    SearchQueryMapper searchQueryMapper;

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSearchQuerySample1();
        var actual = searchQueryMapper.toEntity(searchQueryMapper.toDto(expected));
        assertSearchQueryAllPropertiesEquals(expected, actual);
    }
}
