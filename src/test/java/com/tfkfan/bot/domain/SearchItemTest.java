package com.tfkfan.bot.domain;

import static com.tfkfan.bot.domain.SearchItemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tfkfan.bot.TestUtil;
import org.junit.jupiter.api.Test;

class SearchItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SearchItem.class);
        SearchItem searchItem1 = getSearchItemSample1();
        SearchItem searchItem2 = new SearchItem();
        assertThat(searchItem1).isNotEqualTo(searchItem2);

        searchItem2.href = searchItem1.href;
        assertThat(searchItem1).isEqualTo(searchItem2);

        searchItem2 = getSearchItemSample2();
        assertThat(searchItem1).isNotEqualTo(searchItem2);
    }
}
