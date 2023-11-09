package fr.it_akademy.sneakers.service;

import fr.it_akademy.sneakers.service.dto.DetailsDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.it_akademy.sneakers.domain.Details}.
 */
public interface DetailsService {
    /**
     * Save a details.
     *
     * @param detailsDTO the entity to save.
     * @return the persisted entity.
     */
    DetailsDTO save(DetailsDTO detailsDTO);

    /**
     * Updates a details.
     *
     * @param detailsDTO the entity to update.
     * @return the persisted entity.
     */
    DetailsDTO update(DetailsDTO detailsDTO);

    /**
     * Partially updates a details.
     *
     * @param detailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DetailsDTO> partialUpdate(DetailsDTO detailsDTO);

    /**
     * Get all the details.
     *
     * @return the list of entities.
     */
    List<DetailsDTO> findAll();

    /**
     * Get all the DetailsDTO where Sneakers is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<DetailsDTO> findAllWhereSneakersIsNull();

    /**
     * Get the "id" details.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DetailsDTO> findOne(Long id);

    /**
     * Delete the "id" details.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
