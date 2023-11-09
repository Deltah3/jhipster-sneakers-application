package fr.it_akademy.sneakers.domain;

import static fr.it_akademy.sneakers.domain.ClientTestSamples.*;
import static fr.it_akademy.sneakers.domain.CommandeTestSamples.*;
import static fr.it_akademy.sneakers.domain.SneakersTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.sneakers.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CommandeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commande.class);
        Commande commande1 = getCommandeSample1();
        Commande commande2 = new Commande();
        assertThat(commande1).isNotEqualTo(commande2);

        commande2.setId(commande1.getId());
        assertThat(commande1).isEqualTo(commande2);

        commande2 = getCommandeSample2();
        assertThat(commande1).isNotEqualTo(commande2);
    }

    @Test
    void sneakerssTest() throws Exception {
        Commande commande = getCommandeRandomSampleGenerator();
        Sneakers sneakersBack = getSneakersRandomSampleGenerator();

        commande.addSneakerss(sneakersBack);
        assertThat(commande.getSneakersses()).containsOnly(sneakersBack);

        commande.removeSneakerss(sneakersBack);
        assertThat(commande.getSneakersses()).doesNotContain(sneakersBack);

        commande.sneakersses(new HashSet<>(Set.of(sneakersBack)));
        assertThat(commande.getSneakersses()).containsOnly(sneakersBack);

        commande.setSneakersses(new HashSet<>());
        assertThat(commande.getSneakersses()).doesNotContain(sneakersBack);
    }

    @Test
    void clientTest() throws Exception {
        Commande commande = getCommandeRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        commande.setClient(clientBack);
        assertThat(commande.getClient()).isEqualTo(clientBack);

        commande.client(null);
        assertThat(commande.getClient()).isNull();
    }
}
