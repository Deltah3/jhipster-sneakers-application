package fr.it_akademy.sneakers.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.it_akademy.sneakers.IntegrationTest;
import fr.it_akademy.sneakers.domain.Sneakers;
import fr.it_akademy.sneakers.repository.SneakersRepository;
import fr.it_akademy.sneakers.service.dto.SneakersDTO;
import fr.it_akademy.sneakers.service.mapper.SneakersMapper;
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
 * Integration tests for the {@link SneakersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SneakersResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_COULEUR = "AAAAAAAAAA";
    private static final String UPDATED_COULEUR = "BBBBBBBBBB";

    private static final Long DEFAULT_STOCK = 1L;
    private static final Long UPDATED_STOCK = 2L;

    private static final String ENTITY_API_URL = "/api/sneakers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SneakersRepository sneakersRepository;

    @Autowired
    private SneakersMapper sneakersMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSneakersMockMvc;

    private Sneakers sneakers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sneakers createEntity(EntityManager em) {
        Sneakers sneakers = new Sneakers().nom(DEFAULT_NOM).couleur(DEFAULT_COULEUR).stock(DEFAULT_STOCK);
        return sneakers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sneakers createUpdatedEntity(EntityManager em) {
        Sneakers sneakers = new Sneakers().nom(UPDATED_NOM).couleur(UPDATED_COULEUR).stock(UPDATED_STOCK);
        return sneakers;
    }

    @BeforeEach
    public void initTest() {
        sneakers = createEntity(em);
    }

    @Test
    @Transactional
    void createSneakers() throws Exception {
        int databaseSizeBeforeCreate = sneakersRepository.findAll().size();
        // Create the Sneakers
        SneakersDTO sneakersDTO = sneakersMapper.toDto(sneakers);
        restSneakersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sneakersDTO)))
            .andExpect(status().isCreated());

        // Validate the Sneakers in the database
        List<Sneakers> sneakersList = sneakersRepository.findAll();
        assertThat(sneakersList).hasSize(databaseSizeBeforeCreate + 1);
        Sneakers testSneakers = sneakersList.get(sneakersList.size() - 1);
        assertThat(testSneakers.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testSneakers.getCouleur()).isEqualTo(DEFAULT_COULEUR);
        assertThat(testSneakers.getStock()).isEqualTo(DEFAULT_STOCK);
    }

    @Test
    @Transactional
    void createSneakersWithExistingId() throws Exception {
        // Create the Sneakers with an existing ID
        sneakers.setId(1L);
        SneakersDTO sneakersDTO = sneakersMapper.toDto(sneakers);

        int databaseSizeBeforeCreate = sneakersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSneakersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sneakersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sneakers in the database
        List<Sneakers> sneakersList = sneakersRepository.findAll();
        assertThat(sneakersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSneakers() throws Exception {
        // Initialize the database
        sneakersRepository.saveAndFlush(sneakers);

        // Get all the sneakersList
        restSneakersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sneakers.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].couleur").value(hasItem(DEFAULT_COULEUR)))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK.intValue())));
    }

    @Test
    @Transactional
    void getSneakers() throws Exception {
        // Initialize the database
        sneakersRepository.saveAndFlush(sneakers);

        // Get the sneakers
        restSneakersMockMvc
            .perform(get(ENTITY_API_URL_ID, sneakers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sneakers.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.couleur").value(DEFAULT_COULEUR))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingSneakers() throws Exception {
        // Get the sneakers
        restSneakersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSneakers() throws Exception {
        // Initialize the database
        sneakersRepository.saveAndFlush(sneakers);

        int databaseSizeBeforeUpdate = sneakersRepository.findAll().size();

        // Update the sneakers
        Sneakers updatedSneakers = sneakersRepository.findById(sneakers.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSneakers are not directly saved in db
        em.detach(updatedSneakers);
        updatedSneakers.nom(UPDATED_NOM).couleur(UPDATED_COULEUR).stock(UPDATED_STOCK);
        SneakersDTO sneakersDTO = sneakersMapper.toDto(updatedSneakers);

        restSneakersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sneakersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sneakersDTO))
            )
            .andExpect(status().isOk());

        // Validate the Sneakers in the database
        List<Sneakers> sneakersList = sneakersRepository.findAll();
        assertThat(sneakersList).hasSize(databaseSizeBeforeUpdate);
        Sneakers testSneakers = sneakersList.get(sneakersList.size() - 1);
        assertThat(testSneakers.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSneakers.getCouleur()).isEqualTo(UPDATED_COULEUR);
        assertThat(testSneakers.getStock()).isEqualTo(UPDATED_STOCK);
    }

    @Test
    @Transactional
    void putNonExistingSneakers() throws Exception {
        int databaseSizeBeforeUpdate = sneakersRepository.findAll().size();
        sneakers.setId(longCount.incrementAndGet());

        // Create the Sneakers
        SneakersDTO sneakersDTO = sneakersMapper.toDto(sneakers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSneakersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sneakersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sneakersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sneakers in the database
        List<Sneakers> sneakersList = sneakersRepository.findAll();
        assertThat(sneakersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSneakers() throws Exception {
        int databaseSizeBeforeUpdate = sneakersRepository.findAll().size();
        sneakers.setId(longCount.incrementAndGet());

        // Create the Sneakers
        SneakersDTO sneakersDTO = sneakersMapper.toDto(sneakers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSneakersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sneakersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sneakers in the database
        List<Sneakers> sneakersList = sneakersRepository.findAll();
        assertThat(sneakersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSneakers() throws Exception {
        int databaseSizeBeforeUpdate = sneakersRepository.findAll().size();
        sneakers.setId(longCount.incrementAndGet());

        // Create the Sneakers
        SneakersDTO sneakersDTO = sneakersMapper.toDto(sneakers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSneakersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sneakersDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sneakers in the database
        List<Sneakers> sneakersList = sneakersRepository.findAll();
        assertThat(sneakersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSneakersWithPatch() throws Exception {
        // Initialize the database
        sneakersRepository.saveAndFlush(sneakers);

        int databaseSizeBeforeUpdate = sneakersRepository.findAll().size();

        // Update the sneakers using partial update
        Sneakers partialUpdatedSneakers = new Sneakers();
        partialUpdatedSneakers.setId(sneakers.getId());

        partialUpdatedSneakers.nom(UPDATED_NOM);

        restSneakersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSneakers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSneakers))
            )
            .andExpect(status().isOk());

        // Validate the Sneakers in the database
        List<Sneakers> sneakersList = sneakersRepository.findAll();
        assertThat(sneakersList).hasSize(databaseSizeBeforeUpdate);
        Sneakers testSneakers = sneakersList.get(sneakersList.size() - 1);
        assertThat(testSneakers.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSneakers.getCouleur()).isEqualTo(DEFAULT_COULEUR);
        assertThat(testSneakers.getStock()).isEqualTo(DEFAULT_STOCK);
    }

    @Test
    @Transactional
    void fullUpdateSneakersWithPatch() throws Exception {
        // Initialize the database
        sneakersRepository.saveAndFlush(sneakers);

        int databaseSizeBeforeUpdate = sneakersRepository.findAll().size();

        // Update the sneakers using partial update
        Sneakers partialUpdatedSneakers = new Sneakers();
        partialUpdatedSneakers.setId(sneakers.getId());

        partialUpdatedSneakers.nom(UPDATED_NOM).couleur(UPDATED_COULEUR).stock(UPDATED_STOCK);

        restSneakersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSneakers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSneakers))
            )
            .andExpect(status().isOk());

        // Validate the Sneakers in the database
        List<Sneakers> sneakersList = sneakersRepository.findAll();
        assertThat(sneakersList).hasSize(databaseSizeBeforeUpdate);
        Sneakers testSneakers = sneakersList.get(sneakersList.size() - 1);
        assertThat(testSneakers.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSneakers.getCouleur()).isEqualTo(UPDATED_COULEUR);
        assertThat(testSneakers.getStock()).isEqualTo(UPDATED_STOCK);
    }

    @Test
    @Transactional
    void patchNonExistingSneakers() throws Exception {
        int databaseSizeBeforeUpdate = sneakersRepository.findAll().size();
        sneakers.setId(longCount.incrementAndGet());

        // Create the Sneakers
        SneakersDTO sneakersDTO = sneakersMapper.toDto(sneakers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSneakersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sneakersDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sneakersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sneakers in the database
        List<Sneakers> sneakersList = sneakersRepository.findAll();
        assertThat(sneakersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSneakers() throws Exception {
        int databaseSizeBeforeUpdate = sneakersRepository.findAll().size();
        sneakers.setId(longCount.incrementAndGet());

        // Create the Sneakers
        SneakersDTO sneakersDTO = sneakersMapper.toDto(sneakers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSneakersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sneakersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sneakers in the database
        List<Sneakers> sneakersList = sneakersRepository.findAll();
        assertThat(sneakersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSneakers() throws Exception {
        int databaseSizeBeforeUpdate = sneakersRepository.findAll().size();
        sneakers.setId(longCount.incrementAndGet());

        // Create the Sneakers
        SneakersDTO sneakersDTO = sneakersMapper.toDto(sneakers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSneakersMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sneakersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sneakers in the database
        List<Sneakers> sneakersList = sneakersRepository.findAll();
        assertThat(sneakersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSneakers() throws Exception {
        // Initialize the database
        sneakersRepository.saveAndFlush(sneakers);

        int databaseSizeBeforeDelete = sneakersRepository.findAll().size();

        // Delete the sneakers
        restSneakersMockMvc
            .perform(delete(ENTITY_API_URL_ID, sneakers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sneakers> sneakersList = sneakersRepository.findAll();
        assertThat(sneakersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
