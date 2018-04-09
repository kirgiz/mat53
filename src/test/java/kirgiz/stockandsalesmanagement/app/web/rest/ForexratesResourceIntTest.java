package kirgiz.stockandsalesmanagement.app.web.rest;

import kirgiz.stockandsalesmanagement.app.Matv53App;

import kirgiz.stockandsalesmanagement.app.domain.Forexrates;
import kirgiz.stockandsalesmanagement.app.domain.Currency;
import kirgiz.stockandsalesmanagement.app.repository.ForexratesRepository;
import kirgiz.stockandsalesmanagement.app.service.ForexratesService;
import kirgiz.stockandsalesmanagement.app.repository.search.ForexratesSearchRepository;
import kirgiz.stockandsalesmanagement.app.service.dto.ForexratesDTO;
import kirgiz.stockandsalesmanagement.app.service.mapper.ForexratesMapper;
import kirgiz.stockandsalesmanagement.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static kirgiz.stockandsalesmanagement.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ForexratesResource REST controller.
 *
 * @see ForexratesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Matv53App.class)
public class ForexratesResourceIntTest {

    private static final LocalDate DEFAULT_RATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_STRAIGH_RATE = 1D;
    private static final Double UPDATED_STRAIGH_RATE = 2D;

    @Autowired
    private ForexratesRepository forexratesRepository;

    @Autowired
    private ForexratesMapper forexratesMapper;

    @Autowired
    private ForexratesService forexratesService;

    @Autowired
    private ForexratesSearchRepository forexratesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restForexratesMockMvc;

    private Forexrates forexrates;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ForexratesResource forexratesResource = new ForexratesResource(forexratesService);
        this.restForexratesMockMvc = MockMvcBuilders.standaloneSetup(forexratesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Forexrates createEntity(EntityManager em) {
        Forexrates forexrates = new Forexrates()
            .rateDate(DEFAULT_RATE_DATE)
            .straighRate(DEFAULT_STRAIGH_RATE);
        // Add required entity
        Currency rateForCurrency = CurrencyResourceIntTest.createEntity(em);
        em.persist(rateForCurrency);
        em.flush();
        forexrates.setRateForCurrency(rateForCurrency);
        return forexrates;
    }

    @Before
    public void initTest() {
        forexratesSearchRepository.deleteAll();
        forexrates = createEntity(em);
    }

    @Test
    @Transactional
    public void createForexrates() throws Exception {
        int databaseSizeBeforeCreate = forexratesRepository.findAll().size();

        // Create the Forexrates
        ForexratesDTO forexratesDTO = forexratesMapper.toDto(forexrates);
        restForexratesMockMvc.perform(post("/api/forexrates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forexratesDTO)))
            .andExpect(status().isCreated());

        // Validate the Forexrates in the database
        List<Forexrates> forexratesList = forexratesRepository.findAll();
        assertThat(forexratesList).hasSize(databaseSizeBeforeCreate + 1);
        Forexrates testForexrates = forexratesList.get(forexratesList.size() - 1);
        assertThat(testForexrates.getRateDate()).isEqualTo(DEFAULT_RATE_DATE);
        assertThat(testForexrates.getStraighRate()).isEqualTo(DEFAULT_STRAIGH_RATE);

        // Validate the Forexrates in Elasticsearch
        Forexrates forexratesEs = forexratesSearchRepository.findOne(testForexrates.getId());
        assertThat(forexratesEs).isEqualToComparingFieldByField(testForexrates);
    }

    @Test
    @Transactional
    public void createForexratesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = forexratesRepository.findAll().size();

        // Create the Forexrates with an existing ID
        forexrates.setId(1L);
        ForexratesDTO forexratesDTO = forexratesMapper.toDto(forexrates);

        // An entity with an existing ID cannot be created, so this API call must fail
        restForexratesMockMvc.perform(post("/api/forexrates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forexratesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Forexrates in the database
        List<Forexrates> forexratesList = forexratesRepository.findAll();
        assertThat(forexratesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = forexratesRepository.findAll().size();
        // set the field null
        forexrates.setRateDate(null);

        // Create the Forexrates, which fails.
        ForexratesDTO forexratesDTO = forexratesMapper.toDto(forexrates);

        restForexratesMockMvc.perform(post("/api/forexrates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forexratesDTO)))
            .andExpect(status().isBadRequest());

        List<Forexrates> forexratesList = forexratesRepository.findAll();
        assertThat(forexratesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStraighRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = forexratesRepository.findAll().size();
        // set the field null
        forexrates.setStraighRate(null);

        // Create the Forexrates, which fails.
        ForexratesDTO forexratesDTO = forexratesMapper.toDto(forexrates);

        restForexratesMockMvc.perform(post("/api/forexrates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forexratesDTO)))
            .andExpect(status().isBadRequest());

        List<Forexrates> forexratesList = forexratesRepository.findAll();
        assertThat(forexratesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllForexrates() throws Exception {
        // Initialize the database
        forexratesRepository.saveAndFlush(forexrates);

        // Get all the forexratesList
        restForexratesMockMvc.perform(get("/api/forexrates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(forexrates.getId().intValue())))
            .andExpect(jsonPath("$.[*].rateDate").value(hasItem(DEFAULT_RATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].straighRate").value(hasItem(DEFAULT_STRAIGH_RATE.doubleValue())));
    }

    @Test
    @Transactional
    public void getForexrates() throws Exception {
        // Initialize the database
        forexratesRepository.saveAndFlush(forexrates);

        // Get the forexrates
        restForexratesMockMvc.perform(get("/api/forexrates/{id}", forexrates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(forexrates.getId().intValue()))
            .andExpect(jsonPath("$.rateDate").value(DEFAULT_RATE_DATE.toString()))
            .andExpect(jsonPath("$.straighRate").value(DEFAULT_STRAIGH_RATE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingForexrates() throws Exception {
        // Get the forexrates
        restForexratesMockMvc.perform(get("/api/forexrates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateForexrates() throws Exception {
        // Initialize the database
        forexratesRepository.saveAndFlush(forexrates);
        forexratesSearchRepository.save(forexrates);
        int databaseSizeBeforeUpdate = forexratesRepository.findAll().size();

        // Update the forexrates
        Forexrates updatedForexrates = forexratesRepository.findOne(forexrates.getId());
        updatedForexrates
            .rateDate(UPDATED_RATE_DATE)
            .straighRate(UPDATED_STRAIGH_RATE);
        ForexratesDTO forexratesDTO = forexratesMapper.toDto(updatedForexrates);

        restForexratesMockMvc.perform(put("/api/forexrates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forexratesDTO)))
            .andExpect(status().isOk());

        // Validate the Forexrates in the database
        List<Forexrates> forexratesList = forexratesRepository.findAll();
        assertThat(forexratesList).hasSize(databaseSizeBeforeUpdate);
        Forexrates testForexrates = forexratesList.get(forexratesList.size() - 1);
        assertThat(testForexrates.getRateDate()).isEqualTo(UPDATED_RATE_DATE);
        assertThat(testForexrates.getStraighRate()).isEqualTo(UPDATED_STRAIGH_RATE);

        // Validate the Forexrates in Elasticsearch
        Forexrates forexratesEs = forexratesSearchRepository.findOne(testForexrates.getId());
        assertThat(forexratesEs).isEqualToComparingFieldByField(testForexrates);
    }

    @Test
    @Transactional
    public void updateNonExistingForexrates() throws Exception {
        int databaseSizeBeforeUpdate = forexratesRepository.findAll().size();

        // Create the Forexrates
        ForexratesDTO forexratesDTO = forexratesMapper.toDto(forexrates);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restForexratesMockMvc.perform(put("/api/forexrates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forexratesDTO)))
            .andExpect(status().isCreated());

        // Validate the Forexrates in the database
        List<Forexrates> forexratesList = forexratesRepository.findAll();
        assertThat(forexratesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteForexrates() throws Exception {
        // Initialize the database
        forexratesRepository.saveAndFlush(forexrates);
        forexratesSearchRepository.save(forexrates);
        int databaseSizeBeforeDelete = forexratesRepository.findAll().size();

        // Get the forexrates
        restForexratesMockMvc.perform(delete("/api/forexrates/{id}", forexrates.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean forexratesExistsInEs = forexratesSearchRepository.exists(forexrates.getId());
        assertThat(forexratesExistsInEs).isFalse();

        // Validate the database is empty
        List<Forexrates> forexratesList = forexratesRepository.findAll();
        assertThat(forexratesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchForexrates() throws Exception {
        // Initialize the database
        forexratesRepository.saveAndFlush(forexrates);
        forexratesSearchRepository.save(forexrates);

        // Search the forexrates
        restForexratesMockMvc.perform(get("/api/_search/forexrates?query=id:" + forexrates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(forexrates.getId().intValue())))
            .andExpect(jsonPath("$.[*].rateDate").value(hasItem(DEFAULT_RATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].straighRate").value(hasItem(DEFAULT_STRAIGH_RATE.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Forexrates.class);
        Forexrates forexrates1 = new Forexrates();
        forexrates1.setId(1L);
        Forexrates forexrates2 = new Forexrates();
        forexrates2.setId(forexrates1.getId());
        assertThat(forexrates1).isEqualTo(forexrates2);
        forexrates2.setId(2L);
        assertThat(forexrates1).isNotEqualTo(forexrates2);
        forexrates1.setId(null);
        assertThat(forexrates1).isNotEqualTo(forexrates2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ForexratesDTO.class);
        ForexratesDTO forexratesDTO1 = new ForexratesDTO();
        forexratesDTO1.setId(1L);
        ForexratesDTO forexratesDTO2 = new ForexratesDTO();
        assertThat(forexratesDTO1).isNotEqualTo(forexratesDTO2);
        forexratesDTO2.setId(forexratesDTO1.getId());
        assertThat(forexratesDTO1).isEqualTo(forexratesDTO2);
        forexratesDTO2.setId(2L);
        assertThat(forexratesDTO1).isNotEqualTo(forexratesDTO2);
        forexratesDTO1.setId(null);
        assertThat(forexratesDTO1).isNotEqualTo(forexratesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(forexratesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(forexratesMapper.fromId(null)).isNull();
    }
}
