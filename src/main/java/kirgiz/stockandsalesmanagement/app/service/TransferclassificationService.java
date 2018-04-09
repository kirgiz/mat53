package kirgiz.stockandsalesmanagement.app.service;

import kirgiz.stockandsalesmanagement.app.domain.Transferclassification;
import kirgiz.stockandsalesmanagement.app.repository.TransferclassificationRepository;
import kirgiz.stockandsalesmanagement.app.repository.search.TransferclassificationSearchRepository;
import kirgiz.stockandsalesmanagement.app.service.dto.TransferclassificationDTO;
import kirgiz.stockandsalesmanagement.app.service.mapper.TransferclassificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Transferclassification.
 */
@Service
@Transactional
public class TransferclassificationService {

    private final Logger log = LoggerFactory.getLogger(TransferclassificationService.class);

    private final TransferclassificationRepository transferclassificationRepository;

    private final TransferclassificationMapper transferclassificationMapper;

    private final TransferclassificationSearchRepository transferclassificationSearchRepository;

    public TransferclassificationService(TransferclassificationRepository transferclassificationRepository, TransferclassificationMapper transferclassificationMapper, TransferclassificationSearchRepository transferclassificationSearchRepository) {
        this.transferclassificationRepository = transferclassificationRepository;
        this.transferclassificationMapper = transferclassificationMapper;
        this.transferclassificationSearchRepository = transferclassificationSearchRepository;
    }

    /**
     * Save a transferclassification.
     *
     * @param transferclassificationDTO the entity to save
     * @return the persisted entity
     */
    public TransferclassificationDTO save(TransferclassificationDTO transferclassificationDTO) {
        log.debug("Request to save Transferclassification : {}", transferclassificationDTO);
        Transferclassification transferclassification = transferclassificationMapper.toEntity(transferclassificationDTO);
        transferclassification = transferclassificationRepository.save(transferclassification);
        TransferclassificationDTO result = transferclassificationMapper.toDto(transferclassification);
        transferclassificationSearchRepository.save(transferclassification);
        return result;
    }

    /**
     *  Get all the transferclassifications.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TransferclassificationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Transferclassifications");
        return transferclassificationRepository.findAll(pageable)
            .map(transferclassificationMapper::toDto);
    }

    /**
     *  Get one transferclassification by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public TransferclassificationDTO findOne(Long id) {
        log.debug("Request to get Transferclassification : {}", id);
        Transferclassification transferclassification = transferclassificationRepository.findOne(id);
        return transferclassificationMapper.toDto(transferclassification);
    }

    /**
     *  Delete the  transferclassification by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Transferclassification : {}", id);
        transferclassificationRepository.delete(id);
        transferclassificationSearchRepository.delete(id);
    }

    /**
     * Search for the transferclassification corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TransferclassificationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Transferclassifications for query {}", query);
        Page<Transferclassification> result = transferclassificationSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(transferclassificationMapper::toDto);
    }
}
