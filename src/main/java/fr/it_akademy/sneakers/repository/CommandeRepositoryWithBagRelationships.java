package fr.it_akademy.sneakers.repository;

import fr.it_akademy.sneakers.domain.Commande;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CommandeRepositoryWithBagRelationships {
    Optional<Commande> fetchBagRelationships(Optional<Commande> commande);

    List<Commande> fetchBagRelationships(List<Commande> commandes);

    Page<Commande> fetchBagRelationships(Page<Commande> commandes);
}
