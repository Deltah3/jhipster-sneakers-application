package fr.it_akademy.sneakers.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.it_akademy.sneakers.IntegrationTest;
import fr.it_akademy.sneakers.domain.Details;
import fr.it_akademy.sneakers.repository.DetailsRepository;
import fr.it_akademy.sneakers.service.dto.DetailsDTO;
import fr.it_akademy.sneakers.service.mapper.DetailsMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DetailsResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DetailsRepository detailsRepository;

    @Autowired
    private DetailsMapper detailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDetailsMockMvc;

    private Details details;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Details createEntity(EntityManager em) {
        Details details = new Details().description(DEFAULT_DESCRIPTION).reference(DEFAULT_REFERENCE);
        return details;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Details createUpdatedEntity(EntityManager em) {
        Details details = new Details().description(UPDATED_DESCRIPTION).reference(UPDATED_REFERENCE);
        return details;
    }

    @BeforeEach
    public void initTest() {
        details = createEntity(em);
    }

    @Test
    @Transactional
    void createDetails() throws Exception {
        int databaseSizeBeforeCreate = detailsRepository.findAll().size();
        // Create the Details
        DetailsDTO detailsDTO = detailsMapper.toDto(details);
        restDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailsDTO)))
            .andExpect(status().isCreated());

        // Validate the Details in the database
        List<Details> detailsList = detailsRepository.findAll();
        assertThat(detailsList).hasSize(databaseSizeBeforeCreate + 1);
        Details testDetails = detailsList.get(detailsList.size() - 1);
        assertThat(testDetails.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDetails.getReference()).isEqualTo(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    void createDetailsWithExistingId() throws Exception {
        // Create the Details with an existing ID
        details.setId(1L);
        DetailsDTO detailsDTO = detailsMapper.toDto(details);

        int databaseSizeBeforeCreate = detailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Details in the database
        List<Details> detailsList = detailsRepository.findAll();
        assertThat(detailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDetails() throws Exception {
        // Initialize the database
        detailsRepository.saveAndFlush(details);

        // Get all the detailsList
        restDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(details.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)));
    }

    @Test
    @Transactional
    void getDetails() throws Exception {
        // Initialize the database
        detailsRepository.saveAndFlush(details);

        // Get the details
        restDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, details.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(details.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE));
    }

    @Test
    @Transactional
    void getNonExistingDetails() throws Exception {
        // Get the details
        restDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDetails() throws Exception {
        // Initialize the database
        detailsRepository.saveAndFlush(details);

        int databaseSizeBeforeUpdate = detailsRepository.findAll().size();

        // Update the details
        Details updatedDetails = detailsRepository.findById(details.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDetails are not directly saved in db
        em.detach(updatedDetails);
        updatedDetails.description(UPDATED_DESCRIPTION).reference(UPDATED_REFERENCE);
        DetailsDTO detailsDTO = detailsMapper.toDto(updatedDetails);

        restDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Details in the database
        List<Details> detailsList = detailsRepository.findAll();
        assertThat(detailsList).hasSize(databaseSizeBeforeUpdate);
        Details testDetails = detailsList.get(detailsList.size() - 1);
        assertThat(testDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDetails.getReference()).isEqualTo(UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    void putNonExistingDetails() throws Exception {
        int databaseSizeBeforeUpdate = detailsRepository.findAll().size();
        details.setId(longCount.incrementAndGet());

        // Create the Details
        DetailsDTO detailsDTO = detailsMapper.toDto(details);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Details in the database
        List<Details> detailsList = detailsRepository.findAll();
        assertThat(detailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDetails() throws Exception {
        int databaseSizeBeforeUpdate = detailsRepository.findAll().size();
        details.setId(longCount.incrementAndGet());

        // Create the Details
        DetailsDTO detailsDTO = detailsMapper.toDto(details);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Details in the database
        List<Details> detailsList = detailsRepository.findAll();
        assertThat(detailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDetails() throws Exception {
        int databaseSizeBeforeUpdate = detailsRepository.findAll().size();
        details.setId(longCount.incrementAndGet());

        // Create the Details
        DetailsDTO detailsDTO = detailsMapper.toDto(details);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Details in the database
        List<Details> detailsList = detailsRepository.findAll();
        assertThat(detailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDetailsWithPatch() throws Exception {
        // Initialize the database
        detailsRepository.saveAndFlush(details);

        int databaseSizeBeforeUpdate = detailsRepository.findAll().size();

        // Update the details using partial update
        Details partialUpdatedDetails = new Details();
        partialUpdatedDetails.setId(details.getId());

        partialUpdatedDetails.description(UPDATED_DESCRIPTION);

        restDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetails))
            )
            .andExpect(status().isOk());

        // Validate the Details in the database
        List<Details> detailsList = detailsRepository.findAll();
        assertThat(detailsList).hasSize(databaseSizeBeforeUpdate);
        Details testDetails = detailsList.get(detailsList.size() - 1);
        assertThat(testDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDetails.getReference()).isEqualTo(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    void fullUpdateDetailsWithPatch() throws Exception {
        // Initialize the database
        detailsRepository.saveAndFlush(details);

        int databaseSizeBeforeUpdate = detailsRepository.findAll().size();

        // Update the details using partial update
        Details partialUpdatedDetails = new Details();
        partialUpdatedDetails.setId(details.getId());

        partialUpdatedDetails.description(UPDATED_DESCRIPTION).reference(UPDATED_REFERENCE);

        restDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetails))
            )
            .andExpect(status().isOk());

        // Validate the Details in the database
        List<Details> detailsList = detailsRepository.findAll();
        assertThat(detailsList).hasSize(databaseSizeBeforeUpdate);
        Details testDetails = detailsList.get(detailsList.size() - 1);
        assertThat(testDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDetails.getReference()).isEqualTo(UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    void patchNonExistingDetails() throws Exception {
        int databaseSizeBeforeUpdate = detailsRepository.findAll().size();
        details.setId(longCount.incrementAndGet());

        // Create the Details
        DetailsDTO detailsDTO = detailsMapper.toDto(details);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, detailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Details in the database
        List<Details> detailsList = detailsRepository.findAll();
        assertThat(detailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDetails() throws Exception {
        int databaseSizeBeforeUpdate = detailsRepository.findAll().size();
        details.setId(longCount.incrementAndGet());

        // Create the Details
        DetailsDTO detailsDTO = detailsMapper.toDto(details);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Details in the database
        List<Details> detailsList = detailsRepository.findAll();
        assertThat(detailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDetails() throws Exception {
        int databaseSizeBeforeUpdate = detailsRepository.findAll().size();
        details.setId(longCount.incrementAndGet());

        // Create the Details
        DetailsDTO detailsDTO = detailsMapper.toDto(details);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(detailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Details in the database
        List<Details> detailsList = detailsRepository.findAll();
        assertThat(detailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDetails() throws Exception {
        // Initialize the database
        detailsRepository.saveAndFlush(details);

        int databaseSizeBeforeDelete = detailsRepository.findAll().size();

        // Delete the details
        restDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, details.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Details> detailsList = detailsRepository.findAll();
        assertThat(detailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
