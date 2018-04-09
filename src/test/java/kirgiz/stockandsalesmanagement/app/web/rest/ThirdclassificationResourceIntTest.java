package kirgiz.stockandsalesmanagement.app.web.rest;

import kirgiz.stockandsalesmanagement.app.Matv53App;

import kirgiz.stockandsalesmanagement.app.domain.Thirdclassification;
import kirgiz.stockandsalesmanagement.app.repository.ThirdclassificationRepository;
import kirgiz.stockandsalesmanagement.app.service.ThirdclassificationService;
import kirgiz.stockandsalesmanagement.app.repository.search.ThirdclassificationSearchRepository;
import kirgiz.stockandsalesmanagement.app.service.dto.ThirdclassificationDTO;
import kirgiz.stockandsalesmanagement.app.service.mapper.ThirdclassificationMapper;
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
 * Test class for the ThirdclassificationResource REST controller.
 *
 * @see ThirdclassificationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Matv53App.class)
public class ThirdclassificationResourceIntTest {

    private static final String DEFAULT_CODE = "AAA";
    private static final String UPDATED_CODE = "BBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private ThirdclassificationRepository thirdclassificationRepository;

    @Autowired
    private ThirdclassificationMapper thirdclassificationMapper;

    @Autowired
    private ThirdclassificationService thirdclassificationService;

    @Autowired
    private ThirdclassificationSearchRepository thirdclassificationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restThirdclassificationMockMvc;

    private Thirdclassification thirdclassification;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ThirdclassificationResource thirdclassificationResource = new ThirdclassificationResource(thirdclassificationService);
        this.restThirdclassificationMockMvc = MockMvcBuilders.standaloneSetup(thirdclassificationResource)
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
    public static Thirdclassification createEntity(EntityManager em) {
        Thirdclassification thirdclassification = new Thirdclassification()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .comments(DEFAULT_COMMENTS);
        return thirdclassification;
    }

    @Before
    public void initTest() {
        thirdclassificationSearchRepository.deleteAll();
        thirdclassification = createEntity(em);
    }

    @Test
    @Transactional
    public void createThirdclassification() throws Exception {
        int databaseSizeBeforeCreate = thirdclassificationRepository.findAll().size();

        // Create the Thirdclassification
        ThirdclassificationDTO thirdclassificationDTO = thirdclassificationMapper.toDto(thirdclassification);
        restThirdclassificationMockMvc.perform(post("/api/thirdclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdclassificationDTO)))
            .andExpect(status().isCreated());

        // Validate the Thirdclassification in the database
        List<Thirdclassification> thirdclassificationList = thirdclassificationRepository.findAll();
        assertThat(thirdclassificationList).hasSize(databaseSizeBeforeCreate + 1);
        Thirdclassification testThirdclassification = thirdclassificationList.get(thirdclassificationList.size() - 1);
        assertThat(testThirdclassification.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testThirdclassification.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testThirdclassification.getComments()).isEqualTo(DEFAULT_COMMENTS);

        // Validate the Thirdclassification in Elasticsearch
        Thirdclassification thirdclassificationEs = thirdclassificationSearchRepository.findOne(testThirdclassification.getId());
        assertThat(thirdclassificationEs).isEqualToComparingFieldByField(testThirdclassification);
    }

    @Test
    @Transactional
    public void createThirdclassificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = thirdclassificationRepository.findAll().size();

        // Create the Thirdclassification with an existing ID
        thirdclassification.setId(1L);
        ThirdclassificationDTO thirdclassificationDTO = thirdclassificationMapper.toDto(thirdclassification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThirdclassificationMockMvc.perform(post("/api/thirdclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdclassificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Thirdclassification in the database
        List<Thirdclassification> thirdclassificationList = thirdclassificationRepository.findAll();
        assertThat(thirdclassificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = thirdclassificationRepository.findAll().size();
        // set the field null
        thirdclassification.setCode(null);

        // Create the Thirdclassification, which fails.
        ThirdclassificationDTO thirdclassificationDTO = thirdclassificationMapper.toDto(thirdclassification);

        restThirdclassificationMockMvc.perform(post("/api/thirdclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdclassificationDTO)))
            .andExpect(status().isBadRequest());

        List<Thirdclassification> thirdclassificationList = thirdclassificationRepository.findAll();
        assertThat(thirdclassificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = thirdclassificationRepository.findAll().size();
        // set the field null
        thirdclassification.setName(null);

        // Create the Thirdclassification, which fails.
        ThirdclassificationDTO thirdclassificationDTO = thirdclassificationMapper.toDto(thirdclassification);

        restThirdclassificationMockMvc.perform(post("/api/thirdclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdclassificationDTO)))
            .andExpect(status().isBadRequest());

        List<Thirdclassification> thirdclassificationList = thirdclassificationRepository.findAll();
        assertThat(thirdclassificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThirdclassifications() throws Exception {
        // Initialize the database
        thirdclassificationRepository.saveAndFlush(thirdclassification);

        // Get all the thirdclassificationList
        restThirdclassificationMockMvc.perform(get("/api/thirdclassifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thirdclassification.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }

    @Test
    @Transactional
    public void getThirdclassification() throws Exception {
        // Initialize the database
        thirdclassificationRepository.saveAndFlush(thirdclassification);

        // Get the thirdclassification
        restThirdclassificationMockMvc.perform(get("/api/thirdclassifications/{id}", thirdclassification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(thirdclassification.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingThirdclassification() throws Exception {
        // Get the thirdclassification
        restThirdclassificationMockMvc.perform(get("/api/thirdclassifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThirdclassification() throws Exception {
        // Initialize the database
        thirdclassificationRepository.saveAndFlush(thirdclassification);
        thirdclassificationSearchRepository.save(thirdclassification);
        int databaseSizeBeforeUpdate = thirdclassificationRepository.findAll().size();

        // Update the thirdclassification
        Thirdclassification updatedThirdclassification = thirdclassificationRepository.findOne(thirdclassification.getId());
        updatedThirdclassification
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .comments(UPDATED_COMMENTS);
        ThirdclassificationDTO thirdclassificationDTO = thirdclassificationMapper.toDto(updatedThirdclassification);

        restThirdclassificationMockMvc.perform(put("/api/thirdclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdclassificationDTO)))
            .andExpect(status().isOk());

        // Validate the Thirdclassification in the database
        List<Thirdclassification> thirdclassificationList = thirdclassificationRepository.findAll();
        assertThat(thirdclassificationList).hasSize(databaseSizeBeforeUpdate);
        Thirdclassification testThirdclassification = thirdclassificationList.get(thirdclassificationList.size() - 1);
        assertThat(testThirdclassification.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testThirdclassification.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testThirdclassification.getComments()).isEqualTo(UPDATED_COMMENTS);

        // Validate the Thirdclassification in Elasticsearch
        Thirdclassification thirdclassificationEs = thirdclassificationSearchRepository.findOne(testThirdclassification.getId());
        assertThat(thirdclassificationEs).isEqualToComparingFieldByField(testThirdclassification);
    }

    @Test
    @Transactional
    public void updateNonExistingThirdclassification() throws Exception {
        int databaseSizeBeforeUpdate = thirdclassificationRepository.findAll().size();

        // Create the Thirdclassification
        ThirdclassificationDTO thirdclassificationDTO = thirdclassificationMapper.toDto(thirdclassification);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restThirdclassificationMockMvc.perform(put("/api/thirdclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdclassificationDTO)))
            .andExpect(status().isCreated());

        // Validate the Thirdclassification in the database
        List<Thirdclassification> thirdclassificationList = thirdclassificationRepository.findAll();
        assertThat(thirdclassificationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteThirdclassification() throws Exception {
        // Initialize the database
        thirdclassificationRepository.saveAndFlush(thirdclassification);
        thirdclassificationSearchRepository.save(thirdclassification);
        int databaseSizeBeforeDelete = thirdclassificationRepository.findAll().size();

        // Get the thirdclassification
        restThirdclassificationMockMvc.perform(delete("/api/thirdclassifications/{id}", thirdclassification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean thirdclassificationExistsInEs = thirdclassificationSearchRepository.exists(thirdclassification.getId());
        assertThat(thirdclassificationExistsInEs).isFalse();

        // Validate the database is empty
        List<Thirdclassification> thirdclassificationList = thirdclassificationRepository.findAll();
        assertThat(thirdclassificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchThirdclassification() throws Exception {
        // Initialize the database
        thirdclassificationRepository.saveAndFlush(thirdclassification);
        thirdclassificationSearchRepository.save(thirdclassification);

        // Search the thirdclassification
        restThirdclassificationMockMvc.perform(get("/api/_search/thirdclassifications?query=id:" + thirdclassification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thirdclassification.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Thirdclassification.class);
        Thirdclassification thirdclassification1 = new Thirdclassification();
        thirdclassification1.setId(1L);
        Thirdclassification thirdclassification2 = new Thirdclassification();
        thirdclassification2.setId(thirdclassification1.getId());
        assertThat(thirdclassification1).isEqualTo(thirdclassification2);
        thirdclassification2.setId(2L);
        assertThat(thirdclassification1).isNotEqualTo(thirdclassification2);
        thirdclassification1.setId(null);
        assertThat(thirdclassification1).isNotEqualTo(thirdclassification2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThirdclassificationDTO.class);
        ThirdclassificationDTO thirdclassificationDTO1 = new ThirdclassificationDTO();
        thirdclassificationDTO1.setId(1L);
        ThirdclassificationDTO thirdclassificationDTO2 = new ThirdclassificationDTO();
        assertThat(thirdclassificationDTO1).isNotEqualTo(thirdclassificationDTO2);
        thirdclassificationDTO2.setId(thirdclassificationDTO1.getId());
        assertThat(thirdclassificationDTO1).isEqualTo(thirdclassificationDTO2);
        thirdclassificationDTO2.setId(2L);
        assertThat(thirdclassificationDTO1).isNotEqualTo(thirdclassificationDTO2);
        thirdclassificationDTO1.setId(null);
        assertThat(thirdclassificationDTO1).isNotEqualTo(thirdclassificationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(thirdclassificationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(thirdclassificationMapper.fromId(null)).isNull();
    }
}
