package fr.it_akademy.sneakers.repository;

import fr.it_akademy.sneakers.domain.Sneakers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sneakers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SneakersRepository extends JpaRepository<Sneakers, Long> {}
