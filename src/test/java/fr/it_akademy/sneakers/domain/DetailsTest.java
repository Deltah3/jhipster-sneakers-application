package fr.it_akademy.sneakers.domain;

import static fr.it_akademy.sneakers.domain.DetailsTestSamples.*;
import static fr.it_akademy.sneakers.domain.SneakersTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.sneakers.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Details.class);
        Details details1 = getDetailsSample1();
        Details details2 = new Details();
        assertThat(details1).isNotEqualTo(details2);

        details2.setId(details1.getId());
        assertThat(details1).isEqualTo(details2);

        details2 = getDetailsSample2();
        assertThat(details1).isNotEqualTo(details2);
    }

    @Test
    void sneakersTest() throws Exception {
        Details details = getDetailsRandomSampleGenerator();
        Sneakers sneakersBack = getSneakersRandomSampleGenerator();

        details.setSneakers(sneakersBack);
        assertThat(details.getSneakers()).isEqualTo(sneakersBack);
        assertThat(sneakersBack.getProduits()).isEqualTo(details);

        details.sneakers(null);
        assertThat(details.getSneakers()).isNull();
        assertThat(sneakersBack.getProduits()).isNull();
    }
}
