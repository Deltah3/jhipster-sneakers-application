package fr.it_akademy.sneakers.service;

import fr.it_akademy.sneakers.service.dto.SneakersDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.it_akademy.sneakers.domain.Sneakers}.
 */
public interface SneakersService {
    /**
     * Save a sneakers.
     *
     * @param sneakersDTO the entity to save.
     * @return the persisted entity.
     */
    SneakersDTO save(SneakersDTO sneakersDTO);

    /**
     * Updates a sneakers.
     *
     * @param sneakersDTO the entity to update.
     * @return the persisted entity.
     */
    SneakersDTO update(SneakersDTO sneakersDTO);

    /**
     * Partially updates a sneakers.
     *
     * @param sneakersDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SneakersDTO> partialUpdate(SneakersDTO sneakersDTO);

    /**
     * Get all the sneakers.
     *
     * @return the list of entities.
     */
    List<SneakersDTO> findAll();

    /**
     * Get the "id" sneakers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SneakersDTO> findOne(Long id);

    /**
     * Delete the "id" sneakers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
