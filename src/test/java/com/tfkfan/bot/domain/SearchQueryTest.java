package com.tfkfan.bot.domain;

import static com.tfkfan.bot.domain.SearchQueryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tfkfan.bot.TestUtil;
import org.junit.jupiter.api.Test;

class SearchQueryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SearchQuery.class);
        SearchQuery searchQuery1 = getSearchQuerySample1();
        SearchQuery searchQuery2 = new SearchQuery();
        assertThat(searchQuery1).isNotEqualTo(searchQuery2);

        searchQuery2.id = searchQuery1.id;
        assertThat(searchQuery1).isEqualTo(searchQuery2);

        searchQuery2 = getSearchQuerySample2();
        assertThat(searchQuery1).isNotEqualTo(searchQuery2);
    }
}
