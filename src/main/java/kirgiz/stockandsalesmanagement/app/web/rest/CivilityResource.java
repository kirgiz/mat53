package kirgiz.stockandsalesmanagement.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import kirgiz.stockandsalesmanagement.app.service.CivilityService;
import kirgiz.stockandsalesmanagement.app.web.rest.errors.BadRequestAlertException;
import kirgiz.stockandsalesmanagement.app.web.rest.util.HeaderUtil;
import kirgiz.stockandsalesmanagement.app.service.dto.CivilityDTO;
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
 * REST controller for managing Civility.
 */
@RestController
@RequestMapping("/api")
public class CivilityResource {

    private final Logger log = LoggerFactory.getLogger(CivilityResource.class);

    private static final String ENTITY_NAME = "civility";

    private final CivilityService civilityService;

    public CivilityResource(CivilityService civilityService) {
        this.civilityService = civilityService;
    }

    /**
     * POST  /civilities : Create a new civility.
     *
     * @param civilityDTO the civilityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new civilityDTO, or with status 400 (Bad Request) if the civility has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/civilities")
    @Timed
    public ResponseEntity<CivilityDTO> createCivility(@Valid @RequestBody CivilityDTO civilityDTO) throws URISyntaxException {
        log.debug("REST request to save Civility : {}", civilityDTO);
        if (civilityDTO.getId() != null) {
            throw new BadRequestAlertException("A new civility cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CivilityDTO result = civilityService.save(civilityDTO);
        return ResponseEntity.created(new URI("/api/civilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /civilities : Updates an existing civility.
     *
     * @param civilityDTO the civilityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated civilityDTO,
     * or with status 400 (Bad Request) if the civilityDTO is not valid,
     * or with status 500 (Internal Server Error) if the civilityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/civilities")
    @Timed
    public ResponseEntity<CivilityDTO> updateCivility(@Valid @RequestBody CivilityDTO civilityDTO) throws URISyntaxException {
        log.debug("REST request to update Civility : {}", civilityDTO);
        if (civilityDTO.getId() == null) {
            return createCivility(civilityDTO);
        }
        CivilityDTO result = civilityService.save(civilityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, civilityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /civilities : get all the civilities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of civilities in body
     */
    @GetMapping("/civilities")
    @Timed
    public List<CivilityDTO> getAllCivilities() {
        log.debug("REST request to get all Civilities");
        return civilityService.findAll();
        }

    /**
     * GET  /civilities/:id : get the "id" civility.
     *
     * @param id the id of the civilityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the civilityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/civilities/{id}")
    @Timed
    public ResponseEntity<CivilityDTO> getCivility(@PathVariable Long id) {
        log.debug("REST request to get Civility : {}", id);
        CivilityDTO civilityDTO = civilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(civilityDTO));
    }

    /**
     * DELETE  /civilities/:id : delete the "id" civility.
     *
     * @param id the id of the civilityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/civilities/{id}")
    @Timed
    public ResponseEntity<Void> deleteCivility(@PathVariable Long id) {
        log.debug("REST request to delete Civility : {}", id);
        civilityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/civilities?query=:query : search for the civility corresponding
     * to the query.
     *
     * @param query the query of the civility search
     * @return the result of the search
     */
    @GetMapping("/_search/civilities")
    @Timed
    public List<CivilityDTO> searchCivilities(@RequestParam String query) {
        log.debug("REST request to search Civilities for query {}", query);
        return civilityService.search(query);
    }

}
