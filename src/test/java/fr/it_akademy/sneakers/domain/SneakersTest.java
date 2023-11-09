package fr.it_akademy.sneakers.domain;

import static fr.it_akademy.sneakers.domain.CommandeTestSamples.*;
import static fr.it_akademy.sneakers.domain.DetailsTestSamples.*;
import static fr.it_akademy.sneakers.domain.SneakersTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.sneakers.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SneakersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sneakers.class);
        Sneakers sneakers1 = getSneakersSample1();
        Sneakers sneakers2 = new Sneakers();
        assertThat(sneakers1).isNotEqualTo(sneakers2);

        sneakers2.setId(sneakers1.getId());
        assertThat(sneakers1).isEqualTo(sneakers2);

        sneakers2 = getSneakersSample2();
        assertThat(sneakers1).isNotEqualTo(sneakers2);
    }

    @Test
    void produitsTest() throws Exception {
        Sneakers sneakers = getSneakersRandomSampleGenerator();
        Details detailsBack = getDetailsRandomSampleGenerator();

        sneakers.setProduits(detailsBack);
        assertThat(sneakers.getProduits()).isEqualTo(detailsBack);

        sneakers.produits(null);
        assertThat(sneakers.getProduits()).isNull();
    }

    @Test
    void commandeTest() throws Exception {
        Sneakers sneakers = getSneakersRandomSampleGenerator();
        Commande commandeBack = getCommandeRandomSampleGenerator();

        sneakers.addCommande(commandeBack);
        assertThat(sneakers.getCommandes()).containsOnly(commandeBack);
        assertThat(commandeBack.getSneakersses()).containsOnly(sneakers);

        sneakers.removeCommande(commandeBack);
        assertThat(sneakers.getCommandes()).doesNotContain(commandeBack);
        assertThat(commandeBack.getSneakersses()).doesNotContain(sneakers);

        sneakers.commandes(new HashSet<>(Set.of(commandeBack)));
        assertThat(sneakers.getCommandes()).containsOnly(commandeBack);
        assertThat(commandeBack.getSneakersses()).containsOnly(sneakers);

        sneakers.setCommandes(new HashSet<>());
        assertThat(sneakers.getCommandes()).doesNotContain(commandeBack);
        assertThat(commandeBack.getSneakersses()).doesNotContain(sneakers);
    }
}
