package kirgiz.stockandsalesmanagement.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import kirgiz.stockandsalesmanagement.app.service.MaterialclassificationService;
import kirgiz.stockandsalesmanagement.app.web.rest.errors.BadRequestAlertException;
import kirgiz.stockandsalesmanagement.app.web.rest.util.HeaderUtil;
import kirgiz.stockandsalesmanagement.app.web.rest.util.PaginationUtil;
import kirgiz.stockandsalesmanagement.app.service.dto.MaterialclassificationDTO;
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
 * REST controller for managing Materialclassification.
 */
@RestController
@RequestMapping("/api")
public class MaterialclassificationResource {

    private final Logger log = LoggerFactory.getLogger(MaterialclassificationResource.class);

    private static final String ENTITY_NAME = "materialclassification";

    private final MaterialclassificationService materialclassificationService;

    public MaterialclassificationResource(MaterialclassificationService materialclassificationService) {
        this.materialclassificationService = materialclassificationService;
    }

    /**
     * POST  /materialclassifications : Create a new materialclassification.
     *
     * @param materialclassificationDTO the materialclassificationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new materialclassificationDTO, or with status 400 (Bad Request) if the materialclassification has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/materialclassifications")
    @Timed
    public ResponseEntity<MaterialclassificationDTO> createMaterialclassification(@Valid @RequestBody MaterialclassificationDTO materialclassificationDTO) throws URISyntaxException {
        log.debug("REST request to save Materialclassification : {}", materialclassificationDTO);
        if (materialclassificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new materialclassification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MaterialclassificationDTO result = materialclassificationService.save(materialclassificationDTO);
        return ResponseEntity.created(new URI("/api/materialclassifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /materialclassifications : Updates an existing materialclassification.
     *
     * @param materialclassificationDTO the materialclassificationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated materialclassificationDTO,
     * or with status 400 (Bad Request) if the materialclassificationDTO is not valid,
     * or with status 500 (Internal Server Error) if the materialclassificationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/materialclassifications")
    @Timed
    public ResponseEntity<MaterialclassificationDTO> updateMaterialclassification(@Valid @RequestBody MaterialclassificationDTO materialclassificationDTO) throws URISyntaxException {
        log.debug("REST request to update Materialclassification : {}", materialclassificationDTO);
        if (materialclassificationDTO.getId() == null) {
            return createMaterialclassification(materialclassificationDTO);
        }
        MaterialclassificationDTO result = materialclassificationService.save(materialclassificationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, materialclassificationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /materialclassifications : get all the materialclassifications.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of materialclassifications in body
     */
    @GetMapping("/materialclassifications")
    @Timed
    public ResponseEntity<List<MaterialclassificationDTO>> getAllMaterialclassifications(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Materialclassifications");
        Page<MaterialclassificationDTO> page = materialclassificationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/materialclassifications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /materialclassifications/:id : get the "id" materialclassification.
     *
     * @param id the id of the materialclassificationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the materialclassificationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/materialclassifications/{id}")
    @Timed
    public ResponseEntity<MaterialclassificationDTO> getMaterialclassification(@PathVariable Long id) {
        log.debug("REST request to get Materialclassification : {}", id);
        MaterialclassificationDTO materialclassificationDTO = materialclassificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(materialclassificationDTO));
    }

    /**
     * DELETE  /materialclassifications/:id : delete the "id" materialclassification.
     *
     * @param id the id of the materialclassificationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/materialclassifications/{id}")
    @Timed
    public ResponseEntity<Void> deleteMaterialclassification(@PathVariable Long id) {
        log.debug("REST request to delete Materialclassification : {}", id);
        materialclassificationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/materialclassifications?query=:query : search for the materialclassification corresponding
     * to the query.
     *
     * @param query the query of the materialclassification search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/materialclassifications")
    @Timed
    public ResponseEntity<List<MaterialclassificationDTO>> searchMaterialclassifications(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Materialclassifications for query {}", query);
        Page<MaterialclassificationDTO> page = materialclassificationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/materialclassifications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
