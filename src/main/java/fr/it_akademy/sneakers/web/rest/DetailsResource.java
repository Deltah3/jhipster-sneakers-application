package fr.it_akademy.sneakers.web.rest;

import fr.it_akademy.sneakers.repository.DetailsRepository;
import fr.it_akademy.sneakers.service.DetailsService;
import fr.it_akademy.sneakers.service.dto.DetailsDTO;
import fr.it_akademy.sneakers.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.it_akademy.sneakers.domain.Details}.
 */
@RestController
@RequestMapping("/api/details")
public class DetailsResource {

    private final Logger log = LoggerFactory.getLogger(DetailsResource.class);

    private static final String ENTITY_NAME = "details";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetailsService detailsService;

    private final DetailsRepository detailsRepository;

    public DetailsResource(DetailsService detailsService, DetailsRepository detailsRepository) {
        this.detailsService = detailsService;
        this.detailsRepository = detailsRepository;
    }

    /**
     * {@code POST  /details} : Create a new details.
     *
     * @param detailsDTO the detailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detailsDTO, or with status {@code 400 (Bad Request)} if the details has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DetailsDTO> createDetails(@RequestBody DetailsDTO detailsDTO) throws URISyntaxException {
        log.debug("REST request to save Details : {}", detailsDTO);
        if (detailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new details cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetailsDTO result = detailsService.save(detailsDTO);
        return ResponseEntity
            .created(new URI("/api/details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /details/:id} : Updates an existing details.
     *
     * @param id the id of the detailsDTO to save.
     * @param detailsDTO the detailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailsDTO,
     * or with status {@code 400 (Bad Request)} if the detailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DetailsDTO> updateDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DetailsDTO detailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Details : {}, {}", id, detailsDTO);
        if (detailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DetailsDTO result = detailsService.update(detailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /details/:id} : Partial updates given fields of an existing details, field will ignore if it is null
     *
     * @param id the id of the detailsDTO to save.
     * @param detailsDTO the detailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailsDTO,
     * or with status {@code 400 (Bad Request)} if the detailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the detailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the detailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DetailsDTO> partialUpdateDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DetailsDTO detailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Details partially : {}, {}", id, detailsDTO);
        if (detailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DetailsDTO> result = detailsService.partialUpdate(detailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /details} : get all the details.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of details in body.
     */
    @GetMapping("")
    public List<DetailsDTO> getAllDetails(@RequestParam(required = false) String filter) {
        if ("sneakers-is-null".equals(filter)) {
            log.debug("REST request to get all Detailss where sneakers is null");
            return detailsService.findAllWhereSneakersIsNull();
        }
        log.debug("REST request to get all Details");
        return detailsService.findAll();
    }

    /**
     * {@code GET  /details/:id} : get the "id" details.
     *
     * @param id the id of the detailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DetailsDTO> getDetails(@PathVariable Long id) {
        log.debug("REST request to get Details : {}", id);
        Optional<DetailsDTO> detailsDTO = detailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detailsDTO);
    }

    /**
     * {@code DELETE  /details/:id} : delete the "id" details.
     *
     * @param id the id of the detailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetails(@PathVariable Long id) {
        log.debug("REST request to delete Details : {}", id);
        detailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
