package kirgiz.stockandsalesmanagement.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import kirgiz.stockandsalesmanagement.app.service.ForexratesService;
import kirgiz.stockandsalesmanagement.app.web.rest.errors.BadRequestAlertException;
import kirgiz.stockandsalesmanagement.app.web.rest.util.HeaderUtil;
import kirgiz.stockandsalesmanagement.app.service.dto.ForexratesDTO;
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
 * REST controller for managing Forexrates.
 */
@RestController
@RequestMapping("/api")
public class ForexratesResource {

    private final Logger log = LoggerFactory.getLogger(ForexratesResource.class);

    private static final String ENTITY_NAME = "forexrates";

    private final ForexratesService forexratesService;

    public ForexratesResource(ForexratesService forexratesService) {
        this.forexratesService = forexratesService;
    }

    /**
     * POST  /forexrates : Create a new forexrates.
     *
     * @param forexratesDTO the forexratesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new forexratesDTO, or with status 400 (Bad Request) if the forexrates has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/forexrates")
    @Timed
    public ResponseEntity<ForexratesDTO> createForexrates(@Valid @RequestBody ForexratesDTO forexratesDTO) throws URISyntaxException {
        log.debug("REST request to save Forexrates : {}", forexratesDTO);
        if (forexratesDTO.getId() != null) {
            throw new BadRequestAlertException("A new forexrates cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ForexratesDTO result = forexratesService.save(forexratesDTO);
        return ResponseEntity.created(new URI("/api/forexrates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /forexrates : Updates an existing forexrates.
     *
     * @param forexratesDTO the forexratesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated forexratesDTO,
     * or with status 400 (Bad Request) if the forexratesDTO is not valid,
     * or with status 500 (Internal Server Error) if the forexratesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/forexrates")
    @Timed
    public ResponseEntity<ForexratesDTO> updateForexrates(@Valid @RequestBody ForexratesDTO forexratesDTO) throws URISyntaxException {
        log.debug("REST request to update Forexrates : {}", forexratesDTO);
        if (forexratesDTO.getId() == null) {
            return createForexrates(forexratesDTO);
        }
        ForexratesDTO result = forexratesService.save(forexratesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, forexratesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /forexrates : get all the forexrates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of forexrates in body
     */
    @GetMapping("/forexrates")
    @Timed
    public List<ForexratesDTO> getAllForexrates() {
        log.debug("REST request to get all Forexrates");
        return forexratesService.findAll();
        }

    /**
     * GET  /forexrates/:id : get the "id" forexrates.
     *
     * @param id the id of the forexratesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the forexratesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/forexrates/{id}")
    @Timed
    public ResponseEntity<ForexratesDTO> getForexrates(@PathVariable Long id) {
        log.debug("REST request to get Forexrates : {}", id);
        ForexratesDTO forexratesDTO = forexratesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(forexratesDTO));
    }

    /**
     * DELETE  /forexrates/:id : delete the "id" forexrates.
     *
     * @param id the id of the forexratesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/forexrates/{id}")
    @Timed
    public ResponseEntity<Void> deleteForexrates(@PathVariable Long id) {
        log.debug("REST request to delete Forexrates : {}", id);
        forexratesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/forexrates?query=:query : search for the forexrates corresponding
     * to the query.
     *
     * @param query the query of the forexrates search
     * @return the result of the search
     */
    @GetMapping("/_search/forexrates")
    @Timed
    public List<ForexratesDTO> searchForexrates(@RequestParam String query) {
        log.debug("REST request to search Forexrates for query {}", query);
        return forexratesService.search(query);
    }

}
