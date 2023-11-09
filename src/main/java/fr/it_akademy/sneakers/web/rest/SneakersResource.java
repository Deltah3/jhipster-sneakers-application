package fr.it_akademy.sneakers.web.rest;

import fr.it_akademy.sneakers.repository.SneakersRepository;
import fr.it_akademy.sneakers.service.SneakersService;
import fr.it_akademy.sneakers.service.dto.SneakersDTO;
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
 * REST controller for managing {@link fr.it_akademy.sneakers.domain.Sneakers}.
 */
@RestController
@RequestMapping("/api/sneakers")
public class SneakersResource {

    private final Logger log = LoggerFactory.getLogger(SneakersResource.class);

    private static final String ENTITY_NAME = "sneakers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SneakersService sneakersService;

    private final SneakersRepository sneakersRepository;

    public SneakersResource(SneakersService sneakersService, SneakersRepository sneakersRepository) {
        this.sneakersService = sneakersService;
        this.sneakersRepository = sneakersRepository;
    }

    /**
     * {@code POST  /sneakers} : Create a new sneakers.
     *
     * @param sneakersDTO the sneakersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sneakersDTO, or with status {@code 400 (Bad Request)} if the sneakers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SneakersDTO> createSneakers(@RequestBody SneakersDTO sneakersDTO) throws URISyntaxException {
        log.debug("REST request to save Sneakers : {}", sneakersDTO);
        if (sneakersDTO.getId() != null) {
            throw new BadRequestAlertException("A new sneakers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SneakersDTO result = sneakersService.save(sneakersDTO);
        return ResponseEntity
            .created(new URI("/api/sneakers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sneakers/:id} : Updates an existing sneakers.
     *
     * @param id the id of the sneakersDTO to save.
     * @param sneakersDTO the sneakersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sneakersDTO,
     * or with status {@code 400 (Bad Request)} if the sneakersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sneakersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SneakersDTO> updateSneakers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SneakersDTO sneakersDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Sneakers : {}, {}", id, sneakersDTO);
        if (sneakersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sneakersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sneakersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SneakersDTO result = sneakersService.update(sneakersDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sneakersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sneakers/:id} : Partial updates given fields of an existing sneakers, field will ignore if it is null
     *
     * @param id the id of the sneakersDTO to save.
     * @param sneakersDTO the sneakersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sneakersDTO,
     * or with status {@code 400 (Bad Request)} if the sneakersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sneakersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sneakersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SneakersDTO> partialUpdateSneakers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SneakersDTO sneakersDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sneakers partially : {}, {}", id, sneakersDTO);
        if (sneakersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sneakersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sneakersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SneakersDTO> result = sneakersService.partialUpdate(sneakersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sneakersDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sneakers} : get all the sneakers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sneakers in body.
     */
    @GetMapping("")
    public List<SneakersDTO> getAllSneakers() {
        log.debug("REST request to get all Sneakers");
        return sneakersService.findAll();
    }

    /**
     * {@code GET  /sneakers/:id} : get the "id" sneakers.
     *
     * @param id the id of the sneakersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sneakersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SneakersDTO> getSneakers(@PathVariable Long id) {
        log.debug("REST request to get Sneakers : {}", id);
        Optional<SneakersDTO> sneakersDTO = sneakersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sneakersDTO);
    }

    /**
     * {@code DELETE  /sneakers/:id} : delete the "id" sneakers.
     *
     * @param id the id of the sneakersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSneakers(@PathVariable Long id) {
        log.debug("REST request to delete Sneakers : {}", id);
        sneakersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
