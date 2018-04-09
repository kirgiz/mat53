package kirgiz.stockandsalesmanagement.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import kirgiz.stockandsalesmanagement.app.service.DashboardService;
import kirgiz.stockandsalesmanagement.app.web.rest.errors.BadRequestAlertException;
import kirgiz.stockandsalesmanagement.app.web.rest.util.HeaderUtil;
import kirgiz.stockandsalesmanagement.app.service.dto.DashboardDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Dashboard.
 */
@RestController
@RequestMapping("/api")
public class DashboardResource {

    private final Logger log = LoggerFactory.getLogger(DashboardResource.class);

    private static final String ENTITY_NAME = "dashboard";

    private final DashboardService dashboardService;

    public DashboardResource(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /**
     * POST  /dashboards : Create a new dashboard.
     *
     * @param dashboardDTO the dashboardDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dashboardDTO, or with status 400 (Bad Request) if the dashboard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dashboards")
    @Timed
    public ResponseEntity<DashboardDTO> createDashboard(@Valid @RequestBody DashboardDTO dashboardDTO) throws URISyntaxException {
        log.debug("REST request to save Dashboard : {}", dashboardDTO);
        if (dashboardDTO.getId() != null) {
            throw new BadRequestAlertException("A new dashboard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DashboardDTO result = dashboardService.save(dashboardDTO);
        return ResponseEntity.created(new URI("/api/dashboards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dashboards : Updates an existing dashboard.
     *
     * @param dashboardDTO the dashboardDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dashboardDTO,
     * or with status 400 (Bad Request) if the dashboardDTO is not valid,
     * or with status 500 (Internal Server Error) if the dashboardDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dashboards")
    @Timed
    public ResponseEntity<DashboardDTO> updateDashboard(@Valid @RequestBody DashboardDTO dashboardDTO) throws URISyntaxException {
        log.debug("REST request to update Dashboard : {}", dashboardDTO);
        if (dashboardDTO.getId() == null) {
            return createDashboard(dashboardDTO);
        }
        DashboardDTO result = dashboardService.save(dashboardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dashboardDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dashboards : get all the dashboards.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dashboards in body
     */
    @GetMapping("/dashboards")
    @Timed
    public List<DashboardDTO> getAllDashboards() {
        log.debug("REST request to get all Dashboards");
        return dashboardService.findAll();
        }

    /**
     * GET  /dashboards/:id : get the "id" dashboard.
     *
     * @param id the id of the dashboardDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dashboardDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dashboards/{id}")
    @Timed
    public ResponseEntity<DashboardDTO> getDashboard(@PathVariable Long id) {
        log.debug("REST request to get Dashboard : {}", id);
        DashboardDTO dashboardDTO = dashboardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dashboardDTO));
    }

    /**
     * DELETE  /dashboards/:id : delete the "id" dashboard.
     *
     * @param id the id of the dashboardDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dashboards/{id}")
    @Timed
    public ResponseEntity<Void> deleteDashboard(@PathVariable Long id) {
        log.debug("REST request to delete Dashboard : {}", id);
        dashboardService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/dashboards?query=:query : search for the dashboard corresponding
     * to the query.
     *
     * @param query the query of the dashboard search
     * @return the result of the search
     */
    @GetMapping("/_search/dashboards")
    @Timed
    public List<DashboardDTO> searchDashboards(@RequestParam String query) {
        log.debug("REST request to search Dashboards for query {}", query);
        return dashboardService.search(query);
    }

}
