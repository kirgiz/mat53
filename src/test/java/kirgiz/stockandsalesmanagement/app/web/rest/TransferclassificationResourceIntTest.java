package kirgiz.stockandsalesmanagement.app.web.rest;

import kirgiz.stockandsalesmanagement.app.Matv53App;

import kirgiz.stockandsalesmanagement.app.domain.Transferclassification;
import kirgiz.stockandsalesmanagement.app.repository.TransferclassificationRepository;
import kirgiz.stockandsalesmanagement.app.service.TransferclassificationService;
import kirgiz.stockandsalesmanagement.app.repository.search.TransferclassificationSearchRepository;
import kirgiz.stockandsalesmanagement.app.service.dto.TransferclassificationDTO;
import kirgiz.stockandsalesmanagement.app.service.mapper.TransferclassificationMapper;
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
 * Test class for the TransferclassificationResource REST controller.
 *
 * @see TransferclassificationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Matv53App.class)
public class TransferclassificationResourceIntTest {

    private static final String DEFAULT_CODE = "AAA";
    private static final String UPDATED_CODE = "BBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_OUTGOING_TRANSFER = false;
    private static final Boolean UPDATED_IS_OUTGOING_TRANSFER = true;

    private static final Boolean DEFAULT_IS_INCOMING_TRANSFER = false;
    private static final Boolean UPDATED_IS_INCOMING_TRANSFER = true;

    private static final Boolean DEFAULT_IS_INTERNAL_TRANSFER = false;
    private static final Boolean UPDATED_IS_INTERNAL_TRANSFER = true;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private TransferclassificationRepository transferclassificationRepository;

    @Autowired
    private TransferclassificationMapper transferclassificationMapper;

    @Autowired
    private TransferclassificationService transferclassificationService;

    @Autowired
    private TransferclassificationSearchRepository transferclassificationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTransferclassificationMockMvc;

    private Transferclassification transferclassification;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransferclassificationResource transferclassificationResource = new TransferclassificationResource(transferclassificationService);
        this.restTransferclassificationMockMvc = MockMvcBuilders.standaloneSetup(transferclassificationResource)
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
    public static Transferclassification createEntity(EntityManager em) {
        Transferclassification transferclassification = new Transferclassification()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .isOutgoingTransfer(DEFAULT_IS_OUTGOING_TRANSFER)
            .isIncomingTransfer(DEFAULT_IS_INCOMING_TRANSFER)
            .isInternalTransfer(DEFAULT_IS_INTERNAL_TRANSFER)
            .comments(DEFAULT_COMMENTS);
        return transferclassification;
    }

    @Before
    public void initTest() {
        transferclassificationSearchRepository.deleteAll();
        transferclassification = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransferclassification() throws Exception {
        int databaseSizeBeforeCreate = transferclassificationRepository.findAll().size();

        // Create the Transferclassification
        TransferclassificationDTO transferclassificationDTO = transferclassificationMapper.toDto(transferclassification);
        restTransferclassificationMockMvc.perform(post("/api/transferclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transferclassificationDTO)))
            .andExpect(status().isCreated());

        // Validate the Transferclassification in the database
        List<Transferclassification> transferclassificationList = transferclassificationRepository.findAll();
        assertThat(transferclassificationList).hasSize(databaseSizeBeforeCreate + 1);
        Transferclassification testTransferclassification = transferclassificationList.get(transferclassificationList.size() - 1);
        assertThat(testTransferclassification.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTransferclassification.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTransferclassification.isIsOutgoingTransfer()).isEqualTo(DEFAULT_IS_OUTGOING_TRANSFER);
        assertThat(testTransferclassification.isIsIncomingTransfer()).isEqualTo(DEFAULT_IS_INCOMING_TRANSFER);
        assertThat(testTransferclassification.isIsInternalTransfer()).isEqualTo(DEFAULT_IS_INTERNAL_TRANSFER);
        assertThat(testTransferclassification.getComments()).isEqualTo(DEFAULT_COMMENTS);

        // Validate the Transferclassification in Elasticsearch
        Transferclassification transferclassificationEs = transferclassificationSearchRepository.findOne(testTransferclassification.getId());
        assertThat(transferclassificationEs).isEqualToComparingFieldByField(testTransferclassification);
    }

    @Test
    @Transactional
    public void createTransferclassificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transferclassificationRepository.findAll().size();

        // Create the Transferclassification with an existing ID
        transferclassification.setId(1L);
        TransferclassificationDTO transferclassificationDTO = transferclassificationMapper.toDto(transferclassification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransferclassificationMockMvc.perform(post("/api/transferclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transferclassificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transferclassification in the database
        List<Transferclassification> transferclassificationList = transferclassificationRepository.findAll();
        assertThat(transferclassificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transferclassificationRepository.findAll().size();
        // set the field null
        transferclassification.setCode(null);

        // Create the Transferclassification, which fails.
        TransferclassificationDTO transferclassificationDTO = transferclassificationMapper.toDto(transferclassification);

        restTransferclassificationMockMvc.perform(post("/api/transferclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transferclassificationDTO)))
            .andExpect(status().isBadRequest());

        List<Transferclassification> transferclassificationList = transferclassificationRepository.findAll();
        assertThat(transferclassificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = transferclassificationRepository.findAll().size();
        // set the field null
        transferclassification.setName(null);

        // Create the Transferclassification, which fails.
        TransferclassificationDTO transferclassificationDTO = transferclassificationMapper.toDto(transferclassification);

        restTransferclassificationMockMvc.perform(post("/api/transferclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transferclassificationDTO)))
            .andExpect(status().isBadRequest());

        List<Transferclassification> transferclassificationList = transferclassificationRepository.findAll();
        assertThat(transferclassificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsOutgoingTransferIsRequired() throws Exception {
        int databaseSizeBeforeTest = transferclassificationRepository.findAll().size();
        // set the field null
        transferclassification.setIsOutgoingTransfer(null);

        // Create the Transferclassification, which fails.
        TransferclassificationDTO transferclassificationDTO = transferclassificationMapper.toDto(transferclassification);

        restTransferclassificationMockMvc.perform(post("/api/transferclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transferclassificationDTO)))
            .andExpect(status().isBadRequest());

        List<Transferclassification> transferclassificationList = transferclassificationRepository.findAll();
        assertThat(transferclassificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsIncomingTransferIsRequired() throws Exception {
        int databaseSizeBeforeTest = transferclassificationRepository.findAll().size();
        // set the field null
        transferclassification.setIsIncomingTransfer(null);

        // Create the Transferclassification, which fails.
        TransferclassificationDTO transferclassificationDTO = transferclassificationMapper.toDto(transferclassification);

        restTransferclassificationMockMvc.perform(post("/api/transferclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transferclassificationDTO)))
            .andExpect(status().isBadRequest());

        List<Transferclassification> transferclassificationList = transferclassificationRepository.findAll();
        assertThat(transferclassificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsInternalTransferIsRequired() throws Exception {
        int databaseSizeBeforeTest = transferclassificationRepository.findAll().size();
        // set the field null
        transferclassification.setIsInternalTransfer(null);

        // Create the Transferclassification, which fails.
        TransferclassificationDTO transferclassificationDTO = transferclassificationMapper.toDto(transferclassification);

        restTransferclassificationMockMvc.perform(post("/api/transferclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transferclassificationDTO)))
            .andExpect(status().isBadRequest());

        List<Transferclassification> transferclassificationList = transferclassificationRepository.findAll();
        assertThat(transferclassificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransferclassifications() throws Exception {
        // Initialize the database
        transferclassificationRepository.saveAndFlush(transferclassification);

        // Get all the transferclassificationList
        restTransferclassificationMockMvc.perform(get("/api/transferclassifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transferclassification.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].isOutgoingTransfer").value(hasItem(DEFAULT_IS_OUTGOING_TRANSFER.booleanValue())))
            .andExpect(jsonPath("$.[*].isIncomingTransfer").value(hasItem(DEFAULT_IS_INCOMING_TRANSFER.booleanValue())))
            .andExpect(jsonPath("$.[*].isInternalTransfer").value(hasItem(DEFAULT_IS_INTERNAL_TRANSFER.booleanValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }

    @Test
    @Transactional
    public void getTransferclassification() throws Exception {
        // Initialize the database
        transferclassificationRepository.saveAndFlush(transferclassification);

        // Get the transferclassification
        restTransferclassificationMockMvc.perform(get("/api/transferclassifications/{id}", transferclassification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transferclassification.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.isOutgoingTransfer").value(DEFAULT_IS_OUTGOING_TRANSFER.booleanValue()))
            .andExpect(jsonPath("$.isIncomingTransfer").value(DEFAULT_IS_INCOMING_TRANSFER.booleanValue()))
            .andExpect(jsonPath("$.isInternalTransfer").value(DEFAULT_IS_INTERNAL_TRANSFER.booleanValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransferclassification() throws Exception {
        // Get the transferclassification
        restTransferclassificationMockMvc.perform(get("/api/transferclassifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransferclassification() throws Exception {
        // Initialize the database
        transferclassificationRepository.saveAndFlush(transferclassification);
        transferclassificationSearchRepository.save(transferclassification);
        int databaseSizeBeforeUpdate = transferclassificationRepository.findAll().size();

        // Update the transferclassification
        Transferclassification updatedTransferclassification = transferclassificationRepository.findOne(transferclassification.getId());
        updatedTransferclassification
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .isOutgoingTransfer(UPDATED_IS_OUTGOING_TRANSFER)
            .isIncomingTransfer(UPDATED_IS_INCOMING_TRANSFER)
            .isInternalTransfer(UPDATED_IS_INTERNAL_TRANSFER)
            .comments(UPDATED_COMMENTS);
        TransferclassificationDTO transferclassificationDTO = transferclassificationMapper.toDto(updatedTransferclassification);

        restTransferclassificationMockMvc.perform(put("/api/transferclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transferclassificationDTO)))
            .andExpect(status().isOk());

        // Validate the Transferclassification in the database
        List<Transferclassification> transferclassificationList = transferclassificationRepository.findAll();
        assertThat(transferclassificationList).hasSize(databaseSizeBeforeUpdate);
        Transferclassification testTransferclassification = transferclassificationList.get(transferclassificationList.size() - 1);
        assertThat(testTransferclassification.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTransferclassification.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTransferclassification.isIsOutgoingTransfer()).isEqualTo(UPDATED_IS_OUTGOING_TRANSFER);
        assertThat(testTransferclassification.isIsIncomingTransfer()).isEqualTo(UPDATED_IS_INCOMING_TRANSFER);
        assertThat(testTransferclassification.isIsInternalTransfer()).isEqualTo(UPDATED_IS_INTERNAL_TRANSFER);
        assertThat(testTransferclassification.getComments()).isEqualTo(UPDATED_COMMENTS);

        // Validate the Transferclassification in Elasticsearch
        Transferclassification transferclassificationEs = transferclassificationSearchRepository.findOne(testTransferclassification.getId());
        assertThat(transferclassificationEs).isEqualToComparingFieldByField(testTransferclassification);
    }

    @Test
    @Transactional
    public void updateNonExistingTransferclassification() throws Exception {
        int databaseSizeBeforeUpdate = transferclassificationRepository.findAll().size();

        // Create the Transferclassification
        TransferclassificationDTO transferclassificationDTO = transferclassificationMapper.toDto(transferclassification);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTransferclassificationMockMvc.perform(put("/api/transferclassifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transferclassificationDTO)))
            .andExpect(status().isCreated());

        // Validate the Transferclassification in the database
        List<Transferclassification> transferclassificationList = transferclassificationRepository.findAll();
        assertThat(transferclassificationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTransferclassification() throws Exception {
        // Initialize the database
        transferclassificationRepository.saveAndFlush(transferclassification);
        transferclassificationSearchRepository.save(transferclassification);
        int databaseSizeBeforeDelete = transferclassificationRepository.findAll().size();

        // Get the transferclassification
        restTransferclassificationMockMvc.perform(delete("/api/transferclassifications/{id}", transferclassification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean transferclassificationExistsInEs = transferclassificationSearchRepository.exists(transferclassification.getId());
        assertThat(transferclassificationExistsInEs).isFalse();

        // Validate the database is empty
        List<Transferclassification> transferclassificationList = transferclassificationRepository.findAll();
        assertThat(transferclassificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTransferclassification() throws Exception {
        // Initialize the database
        transferclassificationRepository.saveAndFlush(transferclassification);
        transferclassificationSearchRepository.save(transferclassification);

        // Search the transferclassification
        restTransferclassificationMockMvc.perform(get("/api/_search/transferclassifications?query=id:" + transferclassification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transferclassification.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].isOutgoingTransfer").value(hasItem(DEFAULT_IS_OUTGOING_TRANSFER.booleanValue())))
            .andExpect(jsonPath("$.[*].isIncomingTransfer").value(hasItem(DEFAULT_IS_INCOMING_TRANSFER.booleanValue())))
            .andExpect(jsonPath("$.[*].isInternalTransfer").value(hasItem(DEFAULT_IS_INTERNAL_TRANSFER.booleanValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transferclassification.class);
        Transferclassification transferclassification1 = new Transferclassification();
        transferclassification1.setId(1L);
        Transferclassification transferclassification2 = new Transferclassification();
        transferclassification2.setId(transferclassification1.getId());
        assertThat(transferclassification1).isEqualTo(transferclassification2);
        transferclassification2.setId(2L);
        assertThat(transferclassification1).isNotEqualTo(transferclassification2);
        transferclassification1.setId(null);
        assertThat(transferclassification1).isNotEqualTo(transferclassification2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransferclassificationDTO.class);
        TransferclassificationDTO transferclassificationDTO1 = new TransferclassificationDTO();
        transferclassificationDTO1.setId(1L);
        TransferclassificationDTO transferclassificationDTO2 = new TransferclassificationDTO();
        assertThat(transferclassificationDTO1).isNotEqualTo(transferclassificationDTO2);
        transferclassificationDTO2.setId(transferclassificationDTO1.getId());
        assertThat(transferclassificationDTO1).isEqualTo(transferclassificationDTO2);
        transferclassificationDTO2.setId(2L);
        assertThat(transferclassificationDTO1).isNotEqualTo(transferclassificationDTO2);
        transferclassificationDTO1.setId(null);
        assertThat(transferclassificationDTO1).isNotEqualTo(transferclassificationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transferclassificationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transferclassificationMapper.fromId(null)).isNull();
    }
}
