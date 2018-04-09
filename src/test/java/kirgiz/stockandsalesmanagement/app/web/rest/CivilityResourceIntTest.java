package kirgiz.stockandsalesmanagement.app.web.rest;

import kirgiz.stockandsalesmanagement.app.Matv53App;

import kirgiz.stockandsalesmanagement.app.domain.Civility;
import kirgiz.stockandsalesmanagement.app.repository.CivilityRepository;
import kirgiz.stockandsalesmanagement.app.service.CivilityService;
import kirgiz.stockandsalesmanagement.app.repository.search.CivilitySearchRepository;
import kirgiz.stockandsalesmanagement.app.service.dto.CivilityDTO;
import kirgiz.stockandsalesmanagement.app.service.mapper.CivilityMapper;
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
import java.util.List;

import static kirgiz.stockandsalesmanagement.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CivilityResource REST controller.
 *
 * @see CivilityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Matv53App.class)
public class CivilityResourceIntTest {

    private static final String DEFAULT_CODE = "AAA";
    private static final String UPDATED_CODE = "BBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private CivilityRepository civilityRepository;

    @Autowired
    private CivilityMapper civilityMapper;

    @Autowired
    private CivilityService civilityService;

    @Autowired
    private CivilitySearchRepository civilitySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCivilityMockMvc;

    private Civility civility;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CivilityResource civilityResource = new CivilityResource(civilityService);
        this.restCivilityMockMvc = MockMvcBuilders.standaloneSetup(civilityResource)
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
    public static Civility createEntity(EntityManager em) {
        Civility civility = new Civility()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .comments(DEFAULT_COMMENTS);
        return civility;
    }

    @Before
    public void initTest() {
        civilitySearchRepository.deleteAll();
        civility = createEntity(em);
    }

    @Test
    @Transactional
    public void createCivility() throws Exception {
        int databaseSizeBeforeCreate = civilityRepository.findAll().size();

        // Create the Civility
        CivilityDTO civilityDTO = civilityMapper.toDto(civility);
        restCivilityMockMvc.perform(post("/api/civilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(civilityDTO)))
            .andExpect(status().isCreated());

        // Validate the Civility in the database
        List<Civility> civilityList = civilityRepository.findAll();
        assertThat(civilityList).hasSize(databaseSizeBeforeCreate + 1);
        Civility testCivility = civilityList.get(civilityList.size() - 1);
        assertThat(testCivility.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCivility.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCivility.getComments()).isEqualTo(DEFAULT_COMMENTS);

        // Validate the Civility in Elasticsearch
        Civility civilityEs = civilitySearchRepository.findOne(testCivility.getId());
        assertThat(civilityEs).isEqualToComparingFieldByField(testCivility);
    }

    @Test
    @Transactional
    public void createCivilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = civilityRepository.findAll().size();

        // Create the Civility with an existing ID
        civility.setId(1L);
        CivilityDTO civilityDTO = civilityMapper.toDto(civility);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCivilityMockMvc.perform(post("/api/civilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(civilityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Civility in the database
        List<Civility> civilityList = civilityRepository.findAll();
        assertThat(civilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = civilityRepository.findAll().size();
        // set the field null
        civility.setCode(null);

        // Create the Civility, which fails.
        CivilityDTO civilityDTO = civilityMapper.toDto(civility);

        restCivilityMockMvc.perform(post("/api/civilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(civilityDTO)))
            .andExpect(status().isBadRequest());

        List<Civility> civilityList = civilityRepository.findAll();
        assertThat(civilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = civilityRepository.findAll().size();
        // set the field null
        civility.setName(null);

        // Create the Civility, which fails.
        CivilityDTO civilityDTO = civilityMapper.toDto(civility);

        restCivilityMockMvc.perform(post("/api/civilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(civilityDTO)))
            .andExpect(status().isBadRequest());

        List<Civility> civilityList = civilityRepository.findAll();
        assertThat(civilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCivilities() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get all the civilityList
        restCivilityMockMvc.perform(get("/api/civilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(civility.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }

    @Test
    @Transactional
    public void getCivility() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);

        // Get the civility
        restCivilityMockMvc.perform(get("/api/civilities/{id}", civility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(civility.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCivility() throws Exception {
        // Get the civility
        restCivilityMockMvc.perform(get("/api/civilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCivility() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);
        civilitySearchRepository.save(civility);
        int databaseSizeBeforeUpdate = civilityRepository.findAll().size();

        // Update the civility
        Civility updatedCivility = civilityRepository.findOne(civility.getId());
        updatedCivility
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .comments(UPDATED_COMMENTS);
        CivilityDTO civilityDTO = civilityMapper.toDto(updatedCivility);

        restCivilityMockMvc.perform(put("/api/civilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(civilityDTO)))
            .andExpect(status().isOk());

        // Validate the Civility in the database
        List<Civility> civilityList = civilityRepository.findAll();
        assertThat(civilityList).hasSize(databaseSizeBeforeUpdate);
        Civility testCivility = civilityList.get(civilityList.size() - 1);
        assertThat(testCivility.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCivility.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCivility.getComments()).isEqualTo(UPDATED_COMMENTS);

        // Validate the Civility in Elasticsearch
        Civility civilityEs = civilitySearchRepository.findOne(testCivility.getId());
        assertThat(civilityEs).isEqualToComparingFieldByField(testCivility);
    }

    @Test
    @Transactional
    public void updateNonExistingCivility() throws Exception {
        int databaseSizeBeforeUpdate = civilityRepository.findAll().size();

        // Create the Civility
        CivilityDTO civilityDTO = civilityMapper.toDto(civility);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCivilityMockMvc.perform(put("/api/civilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(civilityDTO)))
            .andExpect(status().isCreated());

        // Validate the Civility in the database
        List<Civility> civilityList = civilityRepository.findAll();
        assertThat(civilityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCivility() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);
        civilitySearchRepository.save(civility);
        int databaseSizeBeforeDelete = civilityRepository.findAll().size();

        // Get the civility
        restCivilityMockMvc.perform(delete("/api/civilities/{id}", civility.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean civilityExistsInEs = civilitySearchRepository.exists(civility.getId());
        assertThat(civilityExistsInEs).isFalse();

        // Validate the database is empty
        List<Civility> civilityList = civilityRepository.findAll();
        assertThat(civilityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCivility() throws Exception {
        // Initialize the database
        civilityRepository.saveAndFlush(civility);
        civilitySearchRepository.save(civility);

        // Search the civility
        restCivilityMockMvc.perform(get("/api/_search/civilities?query=id:" + civility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(civility.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Civility.class);
        Civility civility1 = new Civility();
        civility1.setId(1L);
        Civility civility2 = new Civility();
        civility2.setId(civility1.getId());
        assertThat(civility1).isEqualTo(civility2);
        civility2.setId(2L);
        assertThat(civility1).isNotEqualTo(civility2);
        civility1.setId(null);
        assertThat(civility1).isNotEqualTo(civility2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CivilityDTO.class);
        CivilityDTO civilityDTO1 = new CivilityDTO();
        civilityDTO1.setId(1L);
        CivilityDTO civilityDTO2 = new CivilityDTO();
        assertThat(civilityDTO1).isNotEqualTo(civilityDTO2);
        civilityDTO2.setId(civilityDTO1.getId());
        assertThat(civilityDTO1).isEqualTo(civilityDTO2);
        civilityDTO2.setId(2L);
        assertThat(civilityDTO1).isNotEqualTo(civilityDTO2);
        civilityDTO1.setId(null);
        assertThat(civilityDTO1).isNotEqualTo(civilityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(civilityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(civilityMapper.fromId(null)).isNull();
    }
}
