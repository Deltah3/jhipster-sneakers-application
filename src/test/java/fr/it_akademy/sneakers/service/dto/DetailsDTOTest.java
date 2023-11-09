package fr.it_akademy.sneakers.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.sneakers.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailsDTO.class);
        DetailsDTO detailsDTO1 = new DetailsDTO();
        detailsDTO1.setId(1L);
        DetailsDTO detailsDTO2 = new DetailsDTO();
        assertThat(detailsDTO1).isNotEqualTo(detailsDTO2);
        detailsDTO2.setId(detailsDTO1.getId());
        assertThat(detailsDTO1).isEqualTo(detailsDTO2);
        detailsDTO2.setId(2L);
        assertThat(detailsDTO1).isNotEqualTo(detailsDTO2);
        detailsDTO1.setId(null);
        assertThat(detailsDTO1).isNotEqualTo(detailsDTO2);
    }
}
