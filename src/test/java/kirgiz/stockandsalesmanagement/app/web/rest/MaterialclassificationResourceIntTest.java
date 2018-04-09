package kirgiz.stockandsalesmanagement.app.web.rest;

import kirgiz.stockandsalesmanagement.app.Matv53App;

import kirgiz.stockandsalesmanagement.app.domain.Materialclassification;
import kirgiz.stockandsalesmanagement.app.repository.MaterialclassificationRepository;
import kirgiz.stockandsalesmanagement.app.service.MaterialclassificationService;
import kirgiz.stockandsalesmanagement.app.repository.search.MaterialclassificationSearchRepository;
import kirgiz.stockandsalesmanagement.app.service.dto.MaterialclassificationDTO;
import kirgiz.stockandsalesmanagement.app.service.mapper.MaterialclassificationMapper;
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
 * Test class for the MaterialclassificationResource REST controller.
 *
 * @see MaterialclassificationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Matv53App.class)
public class MaterialclassificationResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private MaterialclassificationRepository materialclassificationRepository;

    @Autowired
    private MaterialclassificationMapper materialclassificationMapper;

    @Autowired
    private MaterialclassificationService materialclassificationService;

    @Autowired
    private MaterialclassificationSearchRepository materialclassificationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMaterialclassificationMockMvc;

    private Materialclassification materialclassification;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MaterialclassificationResource materialclassificationResource = new MaterialclassificationResource(materialclassificationService);
        this.restMaterialclassificationMockMvc = MockMvcBuilders.standaloneSetup(materialclassificationResource)
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
    public static Materialclassification createEntity(EntityManager em) {
        Materialclassification materialclassification = new Materialclassification()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .comments(DEFAULT_COMMENTS);
        return materialclassification;
    }

    @Before
    public void initTest() {
        materialclassificationSearchRepository.deleteAll();
        materialclassification = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaterialclassification() throws Exception {
        int databaseSizeBeforeCreate = materialclassificationRepository.findAll().size();

        // Create the Materialclassification
        MaterialclassificationDTO materialclassificationDTO = materialclassificationMapper.toDto(materialclassification);
        restMaterialclassificationMockMvc.perform(post("/api/materialclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialclassificationDTO)))
            .andExpect(status().isCreated());

        // Validate the Materialclassification in the database
        List<Materialclassification> materialclassificationList = materialclassificationRepository.findAll();
        assertThat(materialclassificationList).hasSize(databaseSizeBeforeCreate + 1);
        Materialclassification testMaterialclassification = materialclassificationList.get(materialclassificationList.size() - 1);
        assertThat(testMaterialclassification.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMaterialclassification.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMaterialclassification.getComments()).isEqualTo(DEFAULT_COMMENTS);

        // Validate the Materialclassification in Elasticsearch
        Materialclassification materialclassificationEs = materialclassificationSearchRepository.findOne(testMaterialclassification.getId());
        assertThat(materialclassificationEs).isEqualToComparingFieldByField(testMaterialclassification);
    }

    @Test
    @Transactional
    public void createMaterialclassificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = materialclassificationRepository.findAll().size();

        // Create the Materialclassification with an existing ID
        materialclassification.setId(1L);
        MaterialclassificationDTO materialclassificationDTO = materialclassificationMapper.toDto(materialclassification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialclassificationMockMvc.perform(post("/api/materialclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialclassificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Materialclassification in the database
        List<Materialclassification> materialclassificationList = materialclassificationRepository.findAll();
        assertThat(materialclassificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialclassificationRepository.findAll().size();
        // set the field null
        materialclassification.setCode(null);

        // Create the Materialclassification, which fails.
        MaterialclassificationDTO materialclassificationDTO = materialclassificationMapper.toDto(materialclassification);

        restMaterialclassificationMockMvc.perform(post("/api/materialclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialclassificationDTO)))
            .andExpect(status().isBadRequest());

        List<Materialclassification> materialclassificationList = materialclassificationRepository.findAll();
        assertThat(materialclassificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialclassificationRepository.findAll().size();
        // set the field null
        materialclassification.setName(null);

        // Create the Materialclassification, which fails.
        MaterialclassificationDTO materialclassificationDTO = materialclassificationMapper.toDto(materialclassification);

        restMaterialclassificationMockMvc.perform(post("/api/materialclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialclassificationDTO)))
            .andExpect(status().isBadRequest());

        List<Materialclassification> materialclassificationList = materialclassificationRepository.findAll();
        assertThat(materialclassificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMaterialclassifications() throws Exception {
        // Initialize the database
        materialclassificationRepository.saveAndFlush(materialclassification);

        // Get all the materialclassificationList
        restMaterialclassificationMockMvc.perform(get("/api/materialclassifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materialclassification.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }

    @Test
    @Transactional
    public void getMaterialclassification() throws Exception {
        // Initialize the database
        materialclassificationRepository.saveAndFlush(materialclassification);

        // Get the materialclassification
        restMaterialclassificationMockMvc.perform(get("/api/materialclassifications/{id}", materialclassification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(materialclassification.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMaterialclassification() throws Exception {
        // Get the materialclassification
        restMaterialclassificationMockMvc.perform(get("/api/materialclassifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaterialclassification() throws Exception {
        // Initialize the database
        materialclassificationRepository.saveAndFlush(materialclassification);
        materialclassificationSearchRepository.save(materialclassification);
        int databaseSizeBeforeUpdate = materialclassificationRepository.findAll().size();

        // Update the materialclassification
        Materialclassification updatedMaterialclassification = materialclassificationRepository.findOne(materialclassification.getId());
        updatedMaterialclassification
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .comments(UPDATED_COMMENTS);
        MaterialclassificationDTO materialclassificationDTO = materialclassificationMapper.toDto(updatedMaterialclassification);

        restMaterialclassificationMockMvc.perform(put("/api/materialclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialclassificationDTO)))
            .andExpect(status().isOk());

        // Validate the Materialclassification in the database
        List<Materialclassification> materialclassificationList = materialclassificationRepository.findAll();
        assertThat(materialclassificationList).hasSize(databaseSizeBeforeUpdate);
        Materialclassification testMaterialclassification = materialclassificationList.get(materialclassificationList.size() - 1);
        assertThat(testMaterialclassification.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMaterialclassification.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMaterialclassification.getComments()).isEqualTo(UPDATED_COMMENTS);

        // Validate the Materialclassification in Elasticsearch
        Materialclassification materialclassificationEs = materialclassificationSearchRepository.findOne(testMaterialclassification.getId());
        assertThat(materialclassificationEs).isEqualToComparingFieldByField(testMaterialclassification);
    }

    @Test
    @Transactional
    public void updateNonExistingMaterialclassification() throws Exception {
        int databaseSizeBeforeUpdate = materialclassificationRepository.findAll().size();

        // Create the Materialclassification
        MaterialclassificationDTO materialclassificationDTO = materialclassificationMapper.toDto(materialclassification);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMaterialclassificationMockMvc.perform(put("/api/materialclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialclassificationDTO)))
            .andExpect(status().isCreated());

        // Validate the Materialclassification in the database
        List<Materialclassification> materialclassificationList = materialclassificationRepository.findAll();
        assertThat(materialclassificationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMaterialclassification() throws Exception {
        // Initialize the database
        materialclassificationRepository.saveAndFlush(materialclassification);
        materialclassificationSearchRepository.save(materialclassification);
        int databaseSizeBeforeDelete = materialclassificationRepository.findAll().size();

        // Get the materialclassification
        restMaterialclassificationMockMvc.perform(delete("/api/materialclassifications/{id}", materialclassification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean materialclassificationExistsInEs = materialclassificationSearchRepository.exists(materialclassification.getId());
        assertThat(materialclassificationExistsInEs).isFalse();

        // Validate the database is empty
        List<Materialclassification> materialclassificationList = materialclassificationRepository.findAll();
        assertThat(materialclassificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMaterialclassification() throws Exception {
        // Initialize the database
        materialclassificationRepository.saveAndFlush(materialclassification);
        materialclassificationSearchRepository.save(materialclassification);

        // Search the materialclassification
        restMaterialclassificationMockMvc.perform(get("/api/_search/materialclassifications?query=id:" + materialclassification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materialclassification.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Materialclassification.class);
        Materialclassification materialclassification1 = new Materialclassification();
        materialclassification1.setId(1L);
        Materialclassification materialclassification2 = new Materialclassification();
        materialclassification2.setId(materialclassification1.getId());
        assertThat(materialclassification1).isEqualTo(materialclassification2);
        materialclassification2.setId(2L);
        assertThat(materialclassification1).isNotEqualTo(materialclassification2);
        materialclassification1.setId(null);
        assertThat(materialclassification1).isNotEqualTo(materialclassification2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterialclassificationDTO.class);
        MaterialclassificationDTO materialclassificationDTO1 = new MaterialclassificationDTO();
        materialclassificationDTO1.setId(1L);
        MaterialclassificationDTO materialclassificationDTO2 = new MaterialclassificationDTO();
        assertThat(materialclassificationDTO1).isNotEqualTo(materialclassificationDTO2);
        materialclassificationDTO2.setId(materialclassificationDTO1.getId());
        assertThat(materialclassificationDTO1).isEqualTo(materialclassificationDTO2);
        materialclassificationDTO2.setId(2L);
        assertThat(materialclassificationDTO1).isNotEqualTo(materialclassificationDTO2);
        materialclassificationDTO1.setId(null);
        assertThat(materialclassificationDTO1).isNotEqualTo(materialclassificationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(materialclassificationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(materialclassificationMapper.fromId(null)).isNull();
    }
}
