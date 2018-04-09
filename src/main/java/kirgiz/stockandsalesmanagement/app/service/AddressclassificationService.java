package kirgiz.stockandsalesmanagement.app.service;

import kirgiz.stockandsalesmanagement.app.domain.Addressclassification;
import kirgiz.stockandsalesmanagement.app.repository.AddressclassificationRepository;
import kirgiz.stockandsalesmanagement.app.repository.search.AddressclassificationSearchRepository;
import kirgiz.stockandsalesmanagement.app.service.dto.AddressclassificationDTO;
import kirgiz.stockandsalesmanagement.app.service.mapper.AddressclassificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Addressclassification.
 */
@Service
@Transactional
public class AddressclassificationService {

    private final Logger log = LoggerFactory.getLogger(AddressclassificationService.class);

    private final AddressclassificationRepository addressclassificationRepository;

    private final AddressclassificationMapper addressclassificationMapper;

    private final AddressclassificationSearchRepository addressclassificationSearchRepository;

    public AddressclassificationService(AddressclassificationRepository addressclassificationRepository, AddressclassificationMapper addressclassificationMapper, AddressclassificationSearchRepository addressclassificationSearchRepository) {
        this.addressclassificationRepository = addressclassificationRepository;
        this.addressclassificationMapper = addressclassificationMapper;
        this.addressclassificationSearchRepository = addressclassificationSearchRepository;
    }

    /**
     * Save a addressclassification.
     *
     * @param addressclassificationDTO the entity to save
     * @return the persisted entity
     */
    public AddressclassificationDTO save(AddressclassificationDTO addressclassificationDTO) {
        log.debug("Request to save Addressclassification : {}", addressclassificationDTO);
        Addressclassification addressclassification = addressclassificationMapper.toEntity(addressclassificationDTO);
        addressclassification = addressclassificationRepository.save(addressclassification);
        AddressclassificationDTO result = addressclassificationMapper.toDto(addressclassification);
        addressclassificationSearchRepository.save(addressclassification);
        return result;
    }

    /**
     *  Get all the addressclassifications.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AddressclassificationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Addressclassifications");
        return addressclassificationRepository.findAll(pageable)
            .map(addressclassificationMapper::toDto);
    }

    /**
     *  Get one addressclassification by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public AddressclassificationDTO findOne(Long id) {
        log.debug("Request to get Addressclassification : {}", id);
        Addressclassification addressclassification = addressclassificationRepository.findOne(id);
        return addressclassificationMapper.toDto(addressclassification);
    }

    /**
     *  Delete the  addressclassification by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Addressclassification : {}", id);
        addressclassificationRepository.delete(id);
        addressclassificationSearchRepository.delete(id);
    }

    /**
     * Search for the addressclassification corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AddressclassificationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Addressclassifications for query {}", query);
        Page<Addressclassification> result = addressclassificationSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(addressclassificationMapper::toDto);
    }
}
