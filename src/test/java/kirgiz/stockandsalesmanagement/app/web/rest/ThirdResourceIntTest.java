package kirgiz.stockandsalesmanagement.app.web.rest;

import kirgiz.stockandsalesmanagement.app.Matv53App;

import kirgiz.stockandsalesmanagement.app.domain.Third;
import kirgiz.stockandsalesmanagement.app.domain.Thirdclassification;
import kirgiz.stockandsalesmanagement.app.domain.Civility;
import kirgiz.stockandsalesmanagement.app.repository.ThirdRepository;
import kirgiz.stockandsalesmanagement.app.service.ThirdService;
import kirgiz.stockandsalesmanagement.app.repository.search.ThirdSearchRepository;
import kirgiz.stockandsalesmanagement.app.service.dto.ThirdDTO;
import kirgiz.stockandsalesmanagement.app.service.mapper.ThirdMapper;
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
 * Test class for the ThirdResource REST controller.
 *
 * @see ThirdResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Matv53App.class)
public class ThirdResourceIntTest {

    private static final String DEFAULT_CODE = "AAA";
    private static final String UPDATED_CODE = "BBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private ThirdRepository thirdRepository;

    @Autowired
    private ThirdMapper thirdMapper;

    @Autowired
    private ThirdService thirdService;

    @Autowired
    private ThirdSearchRepository thirdSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restThirdMockMvc;

    private Third third;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ThirdResource thirdResource = new ThirdResource(thirdService);
        this.restThirdMockMvc = MockMvcBuilders.standaloneSetup(thirdResource)
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
    public static Third createEntity(EntityManager em) {
        Third third = new Third()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .comments(DEFAULT_COMMENTS);
        // Add required entity
        Thirdclassification thirdClassif = ThirdclassificationResourceIntTest.createEntity(em);
        em.persist(thirdClassif);
        em.flush();
        third.setThirdClassif(thirdClassif);
        // Add required entity
        Civility civilityClassif = CivilityResourceIntTest.createEntity(em);
        em.persist(civilityClassif);
        em.flush();
        third.setCivilityClassif(civilityClassif);
        return third;
    }

    @Before
    public void initTest() {
        thirdSearchRepository.deleteAll();
        third = createEntity(em);
    }

    @Test
    @Transactional
    public void createThird() throws Exception {
        int databaseSizeBeforeCreate = thirdRepository.findAll().size();

        // Create the Third
        ThirdDTO thirdDTO = thirdMapper.toDto(third);
        restThirdMockMvc.perform(post("/api/thirds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdDTO)))
            .andExpect(status().isCreated());

        // Validate the Third in the database
        List<Third> thirdList = thirdRepository.findAll();
        assertThat(thirdList).hasSize(databaseSizeBeforeCreate + 1);
        Third testThird = thirdList.get(thirdList.size() - 1);
        assertThat(testThird.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testThird.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testThird.getComments()).isEqualTo(DEFAULT_COMMENTS);

        // Validate the Third in Elasticsearch
        Third thirdEs = thirdSearchRepository.findOne(testThird.getId());
        assertThat(thirdEs).isEqualToComparingFieldByField(testThird);
    }

    @Test
    @Transactional
    public void createThirdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = thirdRepository.findAll().size();

        // Create the Third with an existing ID
        third.setId(1L);
        ThirdDTO thirdDTO = thirdMapper.toDto(third);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThirdMockMvc.perform(post("/api/thirds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Third in the database
        List<Third> thirdList = thirdRepository.findAll();
        assertThat(thirdList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = thirdRepository.findAll().size();
        // set the field null
        third.setCode(null);

        // Create the Third, which fails.
        ThirdDTO thirdDTO = thirdMapper.toDto(third);

        restThirdMockMvc.perform(post("/api/thirds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdDTO)))
            .andExpect(status().isBadRequest());

        List<Third> thirdList = thirdRepository.findAll();
        assertThat(thirdList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = thirdRepository.findAll().size();
        // set the field null
        third.setName(null);

        // Create the Third, which fails.
        ThirdDTO thirdDTO = thirdMapper.toDto(third);

        restThirdMockMvc.perform(post("/api/thirds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdDTO)))
            .andExpect(status().isBadRequest());

        List<Third> thirdList = thirdRepository.findAll();
        assertThat(thirdList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThirds() throws Exception {
        // Initialize the database
        thirdRepository.saveAndFlush(third);

        // Get all the thirdList
        restThirdMockMvc.perform(get("/api/thirds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(third.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }

    @Test
    @Transactional
    public void getThird() throws Exception {
        // Initialize the database
        thirdRepository.saveAndFlush(third);

        // Get the third
        restThirdMockMvc.perform(get("/api/thirds/{id}", third.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(third.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingThird() throws Exception {
        // Get the third
        restThirdMockMvc.perform(get("/api/thirds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThird() throws Exception {
        // Initialize the database
        thirdRepository.saveAndFlush(third);
        thirdSearchRepository.save(third);
        int databaseSizeBeforeUpdate = thirdRepository.findAll().size();

        // Update the third
        Third updatedThird = thirdRepository.findOne(third.getId());
        updatedThird
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .comments(UPDATED_COMMENTS);
        ThirdDTO thirdDTO = thirdMapper.toDto(updatedThird);

        restThirdMockMvc.perform(put("/api/thirds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdDTO)))
            .andExpect(status().isOk());

        // Validate the Third in the database
        List<Third> thirdList = thirdRepository.findAll();
        assertThat(thirdList).hasSize(databaseSizeBeforeUpdate);
        Third testThird = thirdList.get(thirdList.size() - 1);
        assertThat(testThird.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testThird.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testThird.getComments()).isEqualTo(UPDATED_COMMENTS);

        // Validate the Third in Elasticsearch
        Third thirdEs = thirdSearchRepository.findOne(testThird.getId());
        assertThat(thirdEs).isEqualToComparingFieldByField(testThird);
    }

    @Test
    @Transactional
    public void updateNonExistingThird() throws Exception {
        int databaseSizeBeforeUpdate = thirdRepository.findAll().size();

        // Create the Third
        ThirdDTO thirdDTO = thirdMapper.toDto(third);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restThirdMockMvc.perform(put("/api/thirds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdDTO)))
            .andExpect(status().isCreated());

        // Validate the Third in the database
        List<Third> thirdList = thirdRepository.findAll();
        assertThat(thirdList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteThird() throws Exception {
        // Initialize the database
        thirdRepository.saveAndFlush(third);
        thirdSearchRepository.save(third);
        int databaseSizeBeforeDelete = thirdRepository.findAll().size();

        // Get the third
        restThirdMockMvc.perform(delete("/api/thirds/{id}", third.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean thirdExistsInEs = thirdSearchRepository.exists(third.getId());
        assertThat(thirdExistsInEs).isFalse();

        // Validate the database is empty
        List<Third> thirdList = thirdRepository.findAll();
        assertThat(thirdList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchThird() throws Exception {
        // Initialize the database
        thirdRepository.saveAndFlush(third);
        thirdSearchRepository.save(third);

        // Search the third
        restThirdMockMvc.perform(get("/api/_search/thirds?query=id:" + third.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(third.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Third.class);
        Third third1 = new Third();
        third1.setId(1L);
        Third third2 = new Third();
        third2.setId(third1.getId());
        assertThat(third1).isEqualTo(third2);
        third2.setId(2L);
        assertThat(third1).isNotEqualTo(third2);
        third1.setId(null);
        assertThat(third1).isNotEqualTo(third2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThirdDTO.class);
        ThirdDTO thirdDTO1 = new ThirdDTO();
        thirdDTO1.setId(1L);
        ThirdDTO thirdDTO2 = new ThirdDTO();
        assertThat(thirdDTO1).isNotEqualTo(thirdDTO2);
        thirdDTO2.setId(thirdDTO1.getId());
        assertThat(thirdDTO1).isEqualTo(thirdDTO2);
        thirdDTO2.setId(2L);
        assertThat(thirdDTO1).isNotEqualTo(thirdDTO2);
        thirdDTO1.setId(null);
        assertThat(thirdDTO1).isNotEqualTo(thirdDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(thirdMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(thirdMapper.fromId(null)).isNull();
    }
}
