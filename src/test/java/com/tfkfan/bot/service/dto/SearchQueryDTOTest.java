package com.tfkfan.bot.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tfkfan.bot.TestUtil;
import org.junit.jupiter.api.Test;

class SearchQueryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SearchQueryDTO.class);
        SearchQueryDTO searchQueryDTO1 = new SearchQueryDTO();
        searchQueryDTO1.id = 1L;
        SearchQueryDTO searchQueryDTO2 = new SearchQueryDTO();
        assertThat(searchQueryDTO1).isNotEqualTo(searchQueryDTO2);
        searchQueryDTO2.id = searchQueryDTO1.id;
        assertThat(searchQueryDTO1).isEqualTo(searchQueryDTO2);
        searchQueryDTO2.id = 2L;
        assertThat(searchQueryDTO1).isNotEqualTo(searchQueryDTO2);
        searchQueryDTO1.id = null;
        assertThat(searchQueryDTO1).isNotEqualTo(searchQueryDTO2);
    }
}
