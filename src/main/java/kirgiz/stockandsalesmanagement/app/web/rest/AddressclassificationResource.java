package kirgiz.stockandsalesmanagement.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import kirgiz.stockandsalesmanagement.app.service.AddressclassificationService;
import kirgiz.stockandsalesmanagement.app.web.rest.errors.BadRequestAlertException;
import kirgiz.stockandsalesmanagement.app.web.rest.util.HeaderUtil;
import kirgiz.stockandsalesmanagement.app.web.rest.util.PaginationUtil;
import kirgiz.stockandsalesmanagement.app.service.dto.AddressclassificationDTO;
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
 * REST controller for managing Addressclassification.
 */
@RestController
@RequestMapping("/api")
public class AddressclassificationResource {

    private final Logger log = LoggerFactory.getLogger(AddressclassificationResource.class);

    private static final String ENTITY_NAME = "addressclassification";

    private final AddressclassificationService addressclassificationService;

    public AddressclassificationResource(AddressclassificationService addressclassificationService) {
        this.addressclassificationService = addressclassificationService;
    }

    /**
     * POST  /addressclassifications : Create a new addressclassification.
     *
     * @param addressclassificationDTO the addressclassificationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new addressclassificationDTO, or with status 400 (Bad Request) if the addressclassification has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/addressclassifications")
    @Timed
    public ResponseEntity<AddressclassificationDTO> createAddressclassification(@Valid @RequestBody AddressclassificationDTO addressclassificationDTO) throws URISyntaxException {
        log.debug("REST request to save Addressclassification : {}", addressclassificationDTO);
        if (addressclassificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new addressclassification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AddressclassificationDTO result = addressclassificationService.save(addressclassificationDTO);
        return ResponseEntity.created(new URI("/api/addressclassifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /addressclassifications : Updates an existing addressclassification.
     *
     * @param addressclassificationDTO the addressclassificationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated addressclassificationDTO,
     * or with status 400 (Bad Request) if the addressclassificationDTO is not valid,
     * or with status 500 (Internal Server Error) if the addressclassificationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/addressclassifications")
    @Timed
    public ResponseEntity<AddressclassificationDTO> updateAddressclassification(@Valid @RequestBody AddressclassificationDTO addressclassificationDTO) throws URISyntaxException {
        log.debug("REST request to update Addressclassification : {}", addressclassificationDTO);
        if (addressclassificationDTO.getId() == null) {
            return createAddressclassification(addressclassificationDTO);
        }
        AddressclassificationDTO result = addressclassificationService.save(addressclassificationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, addressclassificationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /addressclassifications : get all the addressclassifications.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of addressclassifications in body
     */
    @GetMapping("/addressclassifications")
    @Timed
    public ResponseEntity<List<AddressclassificationDTO>> getAllAddressclassifications(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Addressclassifications");
        Page<AddressclassificationDTO> page = addressclassificationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/addressclassifications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /addressclassifications/:id : get the "id" addressclassification.
     *
     * @param id the id of the addressclassificationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the addressclassificationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/addressclassifications/{id}")
    @Timed
    public ResponseEntity<AddressclassificationDTO> getAddressclassification(@PathVariable Long id) {
        log.debug("REST request to get Addressclassification : {}", id);
        AddressclassificationDTO addressclassificationDTO = addressclassificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(addressclassificationDTO));
    }

    /**
     * DELETE  /addressclassifications/:id : delete the "id" addressclassification.
     *
     * @param id the id of the addressclassificationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/addressclassifications/{id}")
    @Timed
    public ResponseEntity<Void> deleteAddressclassification(@PathVariable Long id) {
        log.debug("REST request to delete Addressclassification : {}", id);
        addressclassificationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/addressclassifications?query=:query : search for the addressclassification corresponding
     * to the query.
     *
     * @param query the query of the addressclassification search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/addressclassifications")
    @Timed
    public ResponseEntity<List<AddressclassificationDTO>> searchAddressclassifications(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Addressclassifications for query {}", query);
        Page<AddressclassificationDTO> page = addressclassificationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/addressclassifications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
