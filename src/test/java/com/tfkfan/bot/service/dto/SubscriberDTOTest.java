package com.tfkfan.bot.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tfkfan.bot.TestUtil;
import org.junit.jupiter.api.Test;

class SubscriberDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubscriberDTO.class);
        SubscriberDTO subscriberDTO1 = new SubscriberDTO();
        subscriberDTO1.id = 1L;
        SubscriberDTO subscriberDTO2 = new SubscriberDTO();
        assertThat(subscriberDTO1).isNotEqualTo(subscriberDTO2);
        subscriberDTO2.id = subscriberDTO1.id;
        assertThat(subscriberDTO1).isEqualTo(subscriberDTO2);
        subscriberDTO2.id = 2L;
        assertThat(subscriberDTO1).isNotEqualTo(subscriberDTO2);
        subscriberDTO1.id = null;
        assertThat(subscriberDTO1).isNotEqualTo(subscriberDTO2);
    }
}
