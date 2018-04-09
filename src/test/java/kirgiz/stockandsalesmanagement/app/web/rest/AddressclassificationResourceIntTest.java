package kirgiz.stockandsalesmanagement.app.web.rest;

import kirgiz.stockandsalesmanagement.app.Matv53App;

import kirgiz.stockandsalesmanagement.app.domain.Addressclassification;
import kirgiz.stockandsalesmanagement.app.repository.AddressclassificationRepository;
import kirgiz.stockandsalesmanagement.app.service.AddressclassificationService;
import kirgiz.stockandsalesmanagement.app.repository.search.AddressclassificationSearchRepository;
import kirgiz.stockandsalesmanagement.app.service.dto.AddressclassificationDTO;
import kirgiz.stockandsalesmanagement.app.service.mapper.AddressclassificationMapper;
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
 * Test class for the AddressclassificationResource REST controller.
 *
 * @see AddressclassificationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Matv53App.class)
public class AddressclassificationResourceIntTest {

    private static final String DEFAULT_CODE = "AAA";
    private static final String UPDATED_CODE = "BBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private AddressclassificationRepository addressclassificationRepository;

    @Autowired
    private AddressclassificationMapper addressclassificationMapper;

    @Autowired
    private AddressclassificationService addressclassificationService;

    @Autowired
    private AddressclassificationSearchRepository addressclassificationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAddressclassificationMockMvc;

    private Addressclassification addressclassification;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AddressclassificationResource addressclassificationResource = new AddressclassificationResource(addressclassificationService);
        this.restAddressclassificationMockMvc = MockMvcBuilders.standaloneSetup(addressclassificationResource)
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
    public static Addressclassification createEntity(EntityManager em) {
        Addressclassification addressclassification = new Addressclassification()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .comments(DEFAULT_COMMENTS);
        return addressclassification;
    }

    @Before
    public void initTest() {
        addressclassificationSearchRepository.deleteAll();
        addressclassification = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddressclassification() throws Exception {
        int databaseSizeBeforeCreate = addressclassificationRepository.findAll().size();

        // Create the Addressclassification
        AddressclassificationDTO addressclassificationDTO = addressclassificationMapper.toDto(addressclassification);
        restAddressclassificationMockMvc.perform(post("/api/addressclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressclassificationDTO)))
            .andExpect(status().isCreated());

        // Validate the Addressclassification in the database
        List<Addressclassification> addressclassificationList = addressclassificationRepository.findAll();
        assertThat(addressclassificationList).hasSize(databaseSizeBeforeCreate + 1);
        Addressclassification testAddressclassification = addressclassificationList.get(addressclassificationList.size() - 1);
        assertThat(testAddressclassification.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAddressclassification.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAddressclassification.getComments()).isEqualTo(DEFAULT_COMMENTS);

        // Validate the Addressclassification in Elasticsearch
        Addressclassification addressclassificationEs = addressclassificationSearchRepository.findOne(testAddressclassification.getId());
        assertThat(addressclassificationEs).isEqualToComparingFieldByField(testAddressclassification);
    }

    @Test
    @Transactional
    public void createAddressclassificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressclassificationRepository.findAll().size();

        // Create the Addressclassification with an existing ID
        addressclassification.setId(1L);
        AddressclassificationDTO addressclassificationDTO = addressclassificationMapper.toDto(addressclassification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressclassificationMockMvc.perform(post("/api/addressclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressclassificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Addressclassification in the database
        List<Addressclassification> addressclassificationList = addressclassificationRepository.findAll();
        assertThat(addressclassificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressclassificationRepository.findAll().size();
        // set the field null
        addressclassification.setCode(null);

        // Create the Addressclassification, which fails.
        AddressclassificationDTO addressclassificationDTO = addressclassificationMapper.toDto(addressclassification);

        restAddressclassificationMockMvc.perform(post("/api/addressclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressclassificationDTO)))
            .andExpect(status().isBadRequest());

        List<Addressclassification> addressclassificationList = addressclassificationRepository.findAll();
        assertThat(addressclassificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressclassificationRepository.findAll().size();
        // set the field null
        addressclassification.setName(null);

        // Create the Addressclassification, which fails.
        AddressclassificationDTO addressclassificationDTO = addressclassificationMapper.toDto(addressclassification);

        restAddressclassificationMockMvc.perform(post("/api/addressclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressclassificationDTO)))
            .andExpect(status().isBadRequest());

        List<Addressclassification> addressclassificationList = addressclassificationRepository.findAll();
        assertThat(addressclassificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAddressclassifications() throws Exception {
        // Initialize the database
        addressclassificationRepository.saveAndFlush(addressclassification);

        // Get all the addressclassificationList
        restAddressclassificationMockMvc.perform(get("/api/addressclassifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addressclassification.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }

    @Test
    @Transactional
    public void getAddressclassification() throws Exception {
        // Initialize the database
        addressclassificationRepository.saveAndFlush(addressclassification);

        // Get the addressclassification
        restAddressclassificationMockMvc.perform(get("/api/addressclassifications/{id}", addressclassification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(addressclassification.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAddressclassification() throws Exception {
        // Get the addressclassification
        restAddressclassificationMockMvc.perform(get("/api/addressclassifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddressclassification() throws Exception {
        // Initialize the database
        addressclassificationRepository.saveAndFlush(addressclassification);
        addressclassificationSearchRepository.save(addressclassification);
        int databaseSizeBeforeUpdate = addressclassificationRepository.findAll().size();

        // Update the addressclassification
        Addressclassification updatedAddressclassification = addressclassificationRepository.findOne(addressclassification.getId());
        updatedAddressclassification
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .comments(UPDATED_COMMENTS);
        AddressclassificationDTO addressclassificationDTO = addressclassificationMapper.toDto(updatedAddressclassification);

        restAddressclassificationMockMvc.perform(put("/api/addressclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressclassificationDTO)))
            .andExpect(status().isOk());

        // Validate the Addressclassification in the database
        List<Addressclassification> addressclassificationList = addressclassificationRepository.findAll();
        assertThat(addressclassificationList).hasSize(databaseSizeBeforeUpdate);
        Addressclassification testAddressclassification = addressclassificationList.get(addressclassificationList.size() - 1);
        assertThat(testAddressclassification.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAddressclassification.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAddressclassification.getComments()).isEqualTo(UPDATED_COMMENTS);

        // Validate the Addressclassification in Elasticsearch
        Addressclassification addressclassificationEs = addressclassificationSearchRepository.findOne(testAddressclassification.getId());
        assertThat(addressclassificationEs).isEqualToComparingFieldByField(testAddressclassification);
    }

    @Test
    @Transactional
    public void updateNonExistingAddressclassification() throws Exception {
        int databaseSizeBeforeUpdate = addressclassificationRepository.findAll().size();

        // Create the Addressclassification
        AddressclassificationDTO addressclassificationDTO = addressclassificationMapper.toDto(addressclassification);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAddressclassificationMockMvc.perform(put("/api/addressclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressclassificationDTO)))
            .andExpect(status().isCreated());

        // Validate the Addressclassification in the database
        List<Addressclassification> addressclassificationList = addressclassificationRepository.findAll();
        assertThat(addressclassificationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAddressclassification() throws Exception {
        // Initialize the database
        addressclassificationRepository.saveAndFlush(addressclassification);
        addressclassificationSearchRepository.save(addressclassification);
        int databaseSizeBeforeDelete = addressclassificationRepository.findAll().size();

        // Get the addressclassification
        restAddressclassificationMockMvc.perform(delete("/api/addressclassifications/{id}", addressclassification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean addressclassificationExistsInEs = addressclassificationSearchRepository.exists(addressclassification.getId());
        assertThat(addressclassificationExistsInEs).isFalse();

        // Validate the database is empty
        List<Addressclassification> addressclassificationList = addressclassificationRepository.findAll();
        assertThat(addressclassificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAddressclassification() throws Exception {
        // Initialize the database
        addressclassificationRepository.saveAndFlush(addressclassification);
        addressclassificationSearchRepository.save(addressclassification);

        // Search the addressclassification
        restAddressclassificationMockMvc.perform(get("/api/_search/addressclassifications?query=id:" + addressclassification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addressclassification.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Addressclassification.class);
        Addressclassification addressclassification1 = new Addressclassification();
        addressclassification1.setId(1L);
        Addressclassification addressclassification2 = new Addressclassification();
        addressclassification2.setId(addressclassification1.getId());
        assertThat(addressclassification1).isEqualTo(addressclassification2);
        addressclassification2.setId(2L);
        assertThat(addressclassification1).isNotEqualTo(addressclassification2);
        addressclassification1.setId(null);
        assertThat(addressclassification1).isNotEqualTo(addressclassification2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressclassificationDTO.class);
        AddressclassificationDTO addressclassificationDTO1 = new AddressclassificationDTO();
        addressclassificationDTO1.setId(1L);
        AddressclassificationDTO addressclassificationDTO2 = new AddressclassificationDTO();
        assertThat(addressclassificationDTO1).isNotEqualTo(addressclassificationDTO2);
        addressclassificationDTO2.setId(addressclassificationDTO1.getId());
        assertThat(addressclassificationDTO1).isEqualTo(addressclassificationDTO2);
        addressclassificationDTO2.setId(2L);
        assertThat(addressclassificationDTO1).isNotEqualTo(addressclassificationDTO2);
        addressclassificationDTO1.setId(null);
        assertThat(addressclassificationDTO1).isNotEqualTo(addressclassificationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(addressclassificationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(addressclassificationMapper.fromId(null)).isNull();
    }
}
