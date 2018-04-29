package kirgiz.stockandsalesmanagement.app.web.rest;

import kirgiz.stockandsalesmanagement.app.Matv53App;

import kirgiz.stockandsalesmanagement.app.domain.Materialhistory;
import kirgiz.stockandsalesmanagement.app.domain.Transferclassification;
import kirgiz.stockandsalesmanagement.app.domain.Third;
import kirgiz.stockandsalesmanagement.app.domain.Third;
import kirgiz.stockandsalesmanagement.app.repository.MaterialhistoryRepository;
import kirgiz.stockandsalesmanagement.app.service.MaterialhistoryService;
import kirgiz.stockandsalesmanagement.app.repository.search.MaterialhistorySearchRepository;
import kirgiz.stockandsalesmanagement.app.service.dto.MaterialhistoryDTO;
import kirgiz.stockandsalesmanagement.app.service.mapper.MaterialhistoryMapper;
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
 * Test class for the MaterialhistoryResource REST controller.
 *
 * @see MaterialhistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Matv53App.class)
public class MaterialhistoryResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_OUTGOING_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_OUTGOING_CURRENCY = "BBBBBBBBBB";

    @Autowired
    private MaterialhistoryRepository materialhistoryRepository;

    @Autowired
    private MaterialhistoryMapper materialhistoryMapper;

    @Autowired
    private MaterialhistoryService materialhistoryService;

    @Autowired
    private MaterialhistorySearchRepository materialhistorySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMaterialhistoryMockMvc;

    private Materialhistory materialhistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MaterialhistoryResource materialhistoryResource = new MaterialhistoryResource(materialhistoryService);
        this.restMaterialhistoryMockMvc = MockMvcBuilders.standaloneSetup(materialhistoryResource)
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
    public static Materialhistory createEntity(EntityManager em) {
        Materialhistory materialhistory = new Materialhistory()
            .code(DEFAULT_CODE)
            .creationDate(DEFAULT_CREATION_DATE)
            .price(DEFAULT_PRICE)
            .comments(DEFAULT_COMMENTS)
            .outgoingCurrency(DEFAULT_OUTGOING_CURRENCY);
        // Add required entity
        Transferclassification transferClassif = TransferclassificationResourceIntTest.createEntity(em);
        em.persist(transferClassif);
        em.flush();
        materialhistory.setTransferClassif(transferClassif);
        // Add required entity
        Third warehousefrom = ThirdResourceIntTest.createEntity(em);
        em.persist(warehousefrom);
        em.flush();
        materialhistory.setWarehousefrom(warehousefrom);
        // Add required entity
        Third warehouseto = ThirdResourceIntTest.createEntity(em);
        em.persist(warehouseto);
        em.flush();
        materialhistory.setWarehouseto(warehouseto);
        return materialhistory;
    }

    @Before
    public void initTest() {
        materialhistorySearchRepository.deleteAll();
        materialhistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaterialhistory() throws Exception {
        int databaseSizeBeforeCreate = materialhistoryRepository.findAll().size();

        // Create the Materialhistory
        MaterialhistoryDTO materialhistoryDTO = materialhistoryMapper.toDto(materialhistory);
        restMaterialhistoryMockMvc.perform(post("/api/materialhistories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialhistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the Materialhistory in the database
        List<Materialhistory> materialhistoryList = materialhistoryRepository.findAll();
        assertThat(materialhistoryList).hasSize(databaseSizeBeforeCreate + 1);
        Materialhistory testMaterialhistory = materialhistoryList.get(materialhistoryList.size() - 1);
        assertThat(testMaterialhistory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMaterialhistory.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testMaterialhistory.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testMaterialhistory.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testMaterialhistory.getOutgoingCurrency()).isEqualTo(DEFAULT_OUTGOING_CURRENCY);

        // Validate the Materialhistory in Elasticsearch
        Materialhistory materialhistoryEs = materialhistorySearchRepository.findOne(testMaterialhistory.getId());
        assertThat(materialhistoryEs).isEqualToComparingFieldByField(testMaterialhistory);
    }

    @Test
    @Transactional
    public void createMaterialhistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = materialhistoryRepository.findAll().size();

        // Create the Materialhistory with an existing ID
        materialhistory.setId(1L);
        MaterialhistoryDTO materialhistoryDTO = materialhistoryMapper.toDto(materialhistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialhistoryMockMvc.perform(post("/api/materialhistories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialhistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Materialhistory in the database
        List<Materialhistory> materialhistoryList = materialhistoryRepository.findAll();
        assertThat(materialhistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialhistoryRepository.findAll().size();
        // set the field null
        materialhistory.setCreationDate(null);

        // Create the Materialhistory, which fails.
        MaterialhistoryDTO materialhistoryDTO = materialhistoryMapper.toDto(materialhistory);

        restMaterialhistoryMockMvc.perform(post("/api/materialhistories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialhistoryDTO)))
            .andExpect(status().isBadRequest());

        List<Materialhistory> materialhistoryList = materialhistoryRepository.findAll();
        assertThat(materialhistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMaterialhistories() throws Exception {
        // Initialize the database
        materialhistoryRepository.saveAndFlush(materialhistory);

        // Get all the materialhistoryList
        restMaterialhistoryMockMvc.perform(get("/api/materialhistories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materialhistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].outgoingCurrency").value(hasItem(DEFAULT_OUTGOING_CURRENCY.toString())));
    }

    @Test
    @Transactional
    public void getMaterialhistory() throws Exception {
        // Initialize the database
        materialhistoryRepository.saveAndFlush(materialhistory);

        // Get the materialhistory
        restMaterialhistoryMockMvc.perform(get("/api/materialhistories/{id}", materialhistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(materialhistory.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.outgoingCurrency").value(DEFAULT_OUTGOING_CURRENCY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMaterialhistory() throws Exception {
        // Get the materialhistory
        restMaterialhistoryMockMvc.perform(get("/api/materialhistories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaterialhistory() throws Exception {
        // Initialize the database
        materialhistoryRepository.saveAndFlush(materialhistory);
        materialhistorySearchRepository.save(materialhistory);
        int databaseSizeBeforeUpdate = materialhistoryRepository.findAll().size();

        // Update the materialhistory
        Materialhistory updatedMaterialhistory = materialhistoryRepository.findOne(materialhistory.getId());
        updatedMaterialhistory
            .code(UPDATED_CODE)
            .creationDate(UPDATED_CREATION_DATE)
            .price(UPDATED_PRICE)
            .comments(UPDATED_COMMENTS)
            .outgoingCurrency(UPDATED_OUTGOING_CURRENCY);
        MaterialhistoryDTO materialhistoryDTO = materialhistoryMapper.toDto(updatedMaterialhistory);

        restMaterialhistoryMockMvc.perform(put("/api/materialhistories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialhistoryDTO)))
            .andExpect(status().isOk());

        // Validate the Materialhistory in the database
        List<Materialhistory> materialhistoryList = materialhistoryRepository.findAll();
        assertThat(materialhistoryList).hasSize(databaseSizeBeforeUpdate);
        Materialhistory testMaterialhistory = materialhistoryList.get(materialhistoryList.size() - 1);
        assertThat(testMaterialhistory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMaterialhistory.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testMaterialhistory.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testMaterialhistory.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testMaterialhistory.getOutgoingCurrency()).isEqualTo(UPDATED_OUTGOING_CURRENCY);

        // Validate the Materialhistory in Elasticsearch
        Materialhistory materialhistoryEs = materialhistorySearchRepository.findOne(testMaterialhistory.getId());
        assertThat(materialhistoryEs).isEqualToComparingFieldByField(testMaterialhistory);
    }

    @Test
    @Transactional
    public void updateNonExistingMaterialhistory() throws Exception {
        int databaseSizeBeforeUpdate = materialhistoryRepository.findAll().size();

        // Create the Materialhistory
        MaterialhistoryDTO materialhistoryDTO = materialhistoryMapper.toDto(materialhistory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMaterialhistoryMockMvc.perform(put("/api/materialhistories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialhistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the Materialhistory in the database
        List<Materialhistory> materialhistoryList = materialhistoryRepository.findAll();
        assertThat(materialhistoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMaterialhistory() throws Exception {
        // Initialize the database
        materialhistoryRepository.saveAndFlush(materialhistory);
        materialhistorySearchRepository.save(materialhistory);
        int databaseSizeBeforeDelete = materialhistoryRepository.findAll().size();

        // Get the materialhistory
        restMaterialhistoryMockMvc.perform(delete("/api/materialhistories/{id}", materialhistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean materialhistoryExistsInEs = materialhistorySearchRepository.exists(materialhistory.getId());
        assertThat(materialhistoryExistsInEs).isFalse();

        // Validate the database is empty
        List<Materialhistory> materialhistoryList = materialhistoryRepository.findAll();
        assertThat(materialhistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMaterialhistory() throws Exception {
        // Initialize the database
        materialhistoryRepository.saveAndFlush(materialhistory);
        materialhistorySearchRepository.save(materialhistory);

        // Search the materialhistory
        restMaterialhistoryMockMvc.perform(get("/api/_search/materialhistories?query=id:" + materialhistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materialhistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].outgoingCurrency").value(hasItem(DEFAULT_OUTGOING_CURRENCY.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Materialhistory.class);
        Materialhistory materialhistory1 = new Materialhistory();
        materialhistory1.setId(1L);
        Materialhistory materialhistory2 = new Materialhistory();
        materialhistory2.setId(materialhistory1.getId());
        assertThat(materialhistory1).isEqualTo(materialhistory2);
        materialhistory2.setId(2L);
        assertThat(materialhistory1).isNotEqualTo(materialhistory2);
        materialhistory1.setId(null);
        assertThat(materialhistory1).isNotEqualTo(materialhistory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterialhistoryDTO.class);
        MaterialhistoryDTO materialhistoryDTO1 = new MaterialhistoryDTO();
        materialhistoryDTO1.setId(1L);
        MaterialhistoryDTO materialhistoryDTO2 = new MaterialhistoryDTO();
        assertThat(materialhistoryDTO1).isNotEqualTo(materialhistoryDTO2);
        materialhistoryDTO2.setId(materialhistoryDTO1.getId());
        assertThat(materialhistoryDTO1).isEqualTo(materialhistoryDTO2);
        materialhistoryDTO2.setId(2L);
        assertThat(materialhistoryDTO1).isNotEqualTo(materialhistoryDTO2);
        materialhistoryDTO1.setId(null);
        assertThat(materialhistoryDTO1).isNotEqualTo(materialhistoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(materialhistoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(materialhistoryMapper.fromId(null)).isNull();
    }
}
