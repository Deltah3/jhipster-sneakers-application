package fr.it_akademy.sneakers.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.sneakers.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SneakersDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SneakersDTO.class);
        SneakersDTO sneakersDTO1 = new SneakersDTO();
        sneakersDTO1.setId(1L);
        SneakersDTO sneakersDTO2 = new SneakersDTO();
        assertThat(sneakersDTO1).isNotEqualTo(sneakersDTO2);
        sneakersDTO2.setId(sneakersDTO1.getId());
        assertThat(sneakersDTO1).isEqualTo(sneakersDTO2);
        sneakersDTO2.setId(2L);
        assertThat(sneakersDTO1).isNotEqualTo(sneakersDTO2);
        sneakersDTO1.setId(null);
        assertThat(sneakersDTO1).isNotEqualTo(sneakersDTO2);
    }
}
