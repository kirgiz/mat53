package kirgiz.stockandsalesmanagement.app.web.rest;

import kirgiz.stockandsalesmanagement.app.Matv53App;

import kirgiz.stockandsalesmanagement.app.domain.Dashboard;
import kirgiz.stockandsalesmanagement.app.domain.Third;
import kirgiz.stockandsalesmanagement.app.domain.Materialclassification;
import kirgiz.stockandsalesmanagement.app.repository.DashboardRepository;
import kirgiz.stockandsalesmanagement.app.service.DashboardService;
import kirgiz.stockandsalesmanagement.app.repository.search.DashboardSearchRepository;
import kirgiz.stockandsalesmanagement.app.service.dto.DashboardDTO;
import kirgiz.stockandsalesmanagement.app.service.mapper.DashboardMapper;
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
 * Test class for the DashboardResource REST controller.
 *
 * @see DashboardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Matv53App.class)
public class DashboardResourceIntTest {

    private static final LocalDate DEFAULT_TRANSFER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRANSFER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_PROFIT_AND_LOSS = 1D;
    private static final Double UPDATED_PROFIT_AND_LOSS = 2D;

    private static final Long DEFAULT_NUMBER_OF_ITEMS = 1L;
    private static final Long UPDATED_NUMBER_OF_ITEMS = 2L;

    private static final Double DEFAULT_CURRENCY = 1D;
    private static final Double UPDATED_CURRENCY = 2D;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private DashboardMapper dashboardMapper;

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private DashboardSearchRepository dashboardSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDashboardMockMvc;

    private Dashboard dashboard;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DashboardResource dashboardResource = new DashboardResource(dashboardService);
        this.restDashboardMockMvc = MockMvcBuilders.standaloneSetup(dashboardResource)
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
    public static Dashboard createEntity(EntityManager em) {
        Dashboard dashboard = new Dashboard()
            .transferDate(DEFAULT_TRANSFER_DATE)
            .profitAndLoss(DEFAULT_PROFIT_AND_LOSS)
            .numberOfItems(DEFAULT_NUMBER_OF_ITEMS)
            .currency(DEFAULT_CURRENCY);
        // Add required entity
        Third warehouseOutg = ThirdResourceIntTest.createEntity(em);
        em.persist(warehouseOutg);
        em.flush();
        dashboard.setWarehouseOutg(warehouseOutg);
        // Add required entity
        Materialclassification materialTypeDefDashboard = MaterialclassificationResourceIntTest.createEntity(em);
        em.persist(materialTypeDefDashboard);
        em.flush();
        dashboard.setMaterialTypeDefDashboard(materialTypeDefDashboard);
        return dashboard;
    }

    @Before
    public void initTest() {
        dashboardSearchRepository.deleteAll();
        dashboard = createEntity(em);
    }

    @Test
    @Transactional
    public void createDashboard() throws Exception {
        int databaseSizeBeforeCreate = dashboardRepository.findAll().size();

        // Create the Dashboard
        DashboardDTO dashboardDTO = dashboardMapper.toDto(dashboard);
        restDashboardMockMvc.perform(post("/api/dashboards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dashboardDTO)))
            .andExpect(status().isCreated());

        // Validate the Dashboard in the database
        List<Dashboard> dashboardList = dashboardRepository.findAll();
        assertThat(dashboardList).hasSize(databaseSizeBeforeCreate + 1);
        Dashboard testDashboard = dashboardList.get(dashboardList.size() - 1);
        assertThat(testDashboard.getTransferDate()).isEqualTo(DEFAULT_TRANSFER_DATE);
        assertThat(testDashboard.getProfitAndLoss()).isEqualTo(DEFAULT_PROFIT_AND_LOSS);
        assertThat(testDashboard.getNumberOfItems()).isEqualTo(DEFAULT_NUMBER_OF_ITEMS);
        assertThat(testDashboard.getCurrency()).isEqualTo(DEFAULT_CURRENCY);

        // Validate the Dashboard in Elasticsearch
        Dashboard dashboardEs = dashboardSearchRepository.findOne(testDashboard.getId());
        assertThat(dashboardEs).isEqualToComparingFieldByField(testDashboard);
    }

    @Test
    @Transactional
    public void createDashboardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dashboardRepository.findAll().size();

        // Create the Dashboard with an existing ID
        dashboard.setId(1L);
        DashboardDTO dashboardDTO = dashboardMapper.toDto(dashboard);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDashboardMockMvc.perform(post("/api/dashboards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dashboardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dashboard in the database
        List<Dashboard> dashboardList = dashboardRepository.findAll();
        assertThat(dashboardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTransferDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = dashboardRepository.findAll().size();
        // set the field null
        dashboard.setTransferDate(null);

        // Create the Dashboard, which fails.
        DashboardDTO dashboardDTO = dashboardMapper.toDto(dashboard);

        restDashboardMockMvc.perform(post("/api/dashboards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dashboardDTO)))
            .andExpect(status().isBadRequest());

        List<Dashboard> dashboardList = dashboardRepository.findAll();
        assertThat(dashboardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDashboards() throws Exception {
        // Initialize the database
        dashboardRepository.saveAndFlush(dashboard);

        // Get all the dashboardList
        restDashboardMockMvc.perform(get("/api/dashboards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dashboard.getId().intValue())))
            .andExpect(jsonPath("$.[*].transferDate").value(hasItem(DEFAULT_TRANSFER_DATE.toString())))
            .andExpect(jsonPath("$.[*].profitAndLoss").value(hasItem(DEFAULT_PROFIT_AND_LOSS.doubleValue())))
            .andExpect(jsonPath("$.[*].numberOfItems").value(hasItem(DEFAULT_NUMBER_OF_ITEMS.intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.doubleValue())));
    }

    @Test
    @Transactional
    public void getDashboard() throws Exception {
        // Initialize the database
        dashboardRepository.saveAndFlush(dashboard);

        // Get the dashboard
        restDashboardMockMvc.perform(get("/api/dashboards/{id}", dashboard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dashboard.getId().intValue()))
            .andExpect(jsonPath("$.transferDate").value(DEFAULT_TRANSFER_DATE.toString()))
            .andExpect(jsonPath("$.profitAndLoss").value(DEFAULT_PROFIT_AND_LOSS.doubleValue()))
            .andExpect(jsonPath("$.numberOfItems").value(DEFAULT_NUMBER_OF_ITEMS.intValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDashboard() throws Exception {
        // Get the dashboard
        restDashboardMockMvc.perform(get("/api/dashboards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDashboard() throws Exception {
        // Initialize the database
        dashboardRepository.saveAndFlush(dashboard);
        dashboardSearchRepository.save(dashboard);
        int databaseSizeBeforeUpdate = dashboardRepository.findAll().size();

        // Update the dashboard
        Dashboard updatedDashboard = dashboardRepository.findOne(dashboard.getId());
        updatedDashboard
            .transferDate(UPDATED_TRANSFER_DATE)
            .profitAndLoss(UPDATED_PROFIT_AND_LOSS)
            .numberOfItems(UPDATED_NUMBER_OF_ITEMS)
            .currency(UPDATED_CURRENCY);
        DashboardDTO dashboardDTO = dashboardMapper.toDto(updatedDashboard);

        restDashboardMockMvc.perform(put("/api/dashboards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dashboardDTO)))
            .andExpect(status().isOk());

        // Validate the Dashboard in the database
        List<Dashboard> dashboardList = dashboardRepository.findAll();
        assertThat(dashboardList).hasSize(databaseSizeBeforeUpdate);
        Dashboard testDashboard = dashboardList.get(dashboardList.size() - 1);
        assertThat(testDashboard.getTransferDate()).isEqualTo(UPDATED_TRANSFER_DATE);
        assertThat(testDashboard.getProfitAndLoss()).isEqualTo(UPDATED_PROFIT_AND_LOSS);
        assertThat(testDashboard.getNumberOfItems()).isEqualTo(UPDATED_NUMBER_OF_ITEMS);
        assertThat(testDashboard.getCurrency()).isEqualTo(UPDATED_CURRENCY);

        // Validate the Dashboard in Elasticsearch
        Dashboard dashboardEs = dashboardSearchRepository.findOne(testDashboard.getId());
        assertThat(dashboardEs).isEqualToComparingFieldByField(testDashboard);
    }

    @Test
    @Transactional
    public void updateNonExistingDashboard() throws Exception {
        int databaseSizeBeforeUpdate = dashboardRepository.findAll().size();

        // Create the Dashboard
        DashboardDTO dashboardDTO = dashboardMapper.toDto(dashboard);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDashboardMockMvc.perform(put("/api/dashboards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dashboardDTO)))
            .andExpect(status().isCreated());

        // Validate the Dashboard in the database
        List<Dashboard> dashboardList = dashboardRepository.findAll();
        assertThat(dashboardList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDashboard() throws Exception {
        // Initialize the database
        dashboardRepository.saveAndFlush(dashboard);
        dashboardSearchRepository.save(dashboard);
        int databaseSizeBeforeDelete = dashboardRepository.findAll().size();

        // Get the dashboard
        restDashboardMockMvc.perform(delete("/api/dashboards/{id}", dashboard.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean dashboardExistsInEs = dashboardSearchRepository.exists(dashboard.getId());
        assertThat(dashboardExistsInEs).isFalse();

        // Validate the database is empty
        List<Dashboard> dashboardList = dashboardRepository.findAll();
        assertThat(dashboardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDashboard() throws Exception {
        // Initialize the database
        dashboardRepository.saveAndFlush(dashboard);
        dashboardSearchRepository.save(dashboard);

        // Search the dashboard
        restDashboardMockMvc.perform(get("/api/_search/dashboards?query=id:" + dashboard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dashboard.getId().intValue())))
            .andExpect(jsonPath("$.[*].transferDate").value(hasItem(DEFAULT_TRANSFER_DATE.toString())))
            .andExpect(jsonPath("$.[*].profitAndLoss").value(hasItem(DEFAULT_PROFIT_AND_LOSS.doubleValue())))
            .andExpect(jsonPath("$.[*].numberOfItems").value(hasItem(DEFAULT_NUMBER_OF_ITEMS.intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dashboard.class);
        Dashboard dashboard1 = new Dashboard();
        dashboard1.setId(1L);
        Dashboard dashboard2 = new Dashboard();
        dashboard2.setId(dashboard1.getId());
        assertThat(dashboard1).isEqualTo(dashboard2);
        dashboard2.setId(2L);
        assertThat(dashboard1).isNotEqualTo(dashboard2);
        dashboard1.setId(null);
        assertThat(dashboard1).isNotEqualTo(dashboard2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DashboardDTO.class);
        DashboardDTO dashboardDTO1 = new DashboardDTO();
        dashboardDTO1.setId(1L);
        DashboardDTO dashboardDTO2 = new DashboardDTO();
        assertThat(dashboardDTO1).isNotEqualTo(dashboardDTO2);
        dashboardDTO2.setId(dashboardDTO1.getId());
        assertThat(dashboardDTO1).isEqualTo(dashboardDTO2);
        dashboardDTO2.setId(2L);
        assertThat(dashboardDTO1).isNotEqualTo(dashboardDTO2);
        dashboardDTO1.setId(null);
        assertThat(dashboardDTO1).isNotEqualTo(dashboardDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dashboardMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dashboardMapper.fromId(null)).isNull();
    }
}
