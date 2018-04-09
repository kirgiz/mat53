package kirgiz.stockandsalesmanagement.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import kirgiz.stockandsalesmanagement.app.service.LotService;
import kirgiz.stockandsalesmanagement.app.web.rest.errors.BadRequestAlertException;
import kirgiz.stockandsalesmanagement.app.web.rest.util.HeaderUtil;
import kirgiz.stockandsalesmanagement.app.web.rest.util.PaginationUtil;
import kirgiz.stockandsalesmanagement.app.service.dto.LotDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
 * REST controller for managing Lot.
 */
@RestController
@RequestMapping("/api")
public class LotResource {

    private final Logger log = LoggerFactory.getLogger(LotResource.class);

    private static final String ENTITY_NAME = "lot";

    private final LotService lotService;

    public LotResource(LotService lotService) {
        this.lotService = lotService;
    }

    /**
     * POST  /lots : Create a new lot.
     *
     * @param lotDTO the lotDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lotDTO, or with status 400 (Bad Request) if the lot has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lots")
    @Timed
    public ResponseEntity<LotDTO> createLot(@Valid @RequestBody LotDTO lotDTO) throws URISyntaxException {
        log.debug("REST request to save Lot : {}", lotDTO);
        if (lotDTO.getId() != null) {
            throw new BadRequestAlertException("A new lot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LotDTO result = lotService.save(lotDTO);
        return ResponseEntity.created(new URI("/api/lots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lots : Updates an existing lot.
     *
     * @param lotDTO the lotDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lotDTO,
     * or with status 400 (Bad Request) if the lotDTO is not valid,
     * or with status 500 (Internal Server Error) if the lotDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lots")
    @Timed
    public ResponseEntity<LotDTO> updateLot(@Valid @RequestBody LotDTO lotDTO) throws URISyntaxException {
        log.debug("REST request to update Lot : {}", lotDTO);
        if (lotDTO.getId() == null) {
            return createLot(lotDTO);
        }
        LotDTO result = lotService.save(lotDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lotDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lots : get all the lots.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lots in body
     */
    @GetMapping("/lots")
    @Timed
    public ResponseEntity<List<LotDTO>> getAllLots(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Lots");
        Page<LotDTO> page = lotService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lots");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /lots/:id : get the "id" lot.
     *
     * @param id the id of the lotDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lotDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lots/{id}")
    @Timed
    public ResponseEntity<LotDTO> getLot(@PathVariable Long id) {
        log.debug("REST request to get Lot : {}", id);
        LotDTO lotDTO = lotService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lotDTO));
    }

    /**
     * DELETE  /lots/:id : delete the "id" lot.
     *
     * @param id the id of the lotDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lots/{id}")
    @Timed
    public ResponseEntity<Void> deleteLot(@PathVariable Long id) {
        log.debug("REST request to delete Lot : {}", id);
        lotService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/lots?query=:query : search for the lot corresponding
     * to the query.
     *
     * @param query the query of the lot search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/lots")
    @Timed
    public ResponseEntity<List<LotDTO>> searchLots(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Lots for query {}", query);
        Page<LotDTO> page = lotService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/lots");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
