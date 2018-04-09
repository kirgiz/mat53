package kirgiz.stockandsalesmanagement.app.service;

import kirgiz.stockandsalesmanagement.app.domain.Materialclassification;
import kirgiz.stockandsalesmanagement.app.repository.MaterialclassificationRepository;
import kirgiz.stockandsalesmanagement.app.repository.search.MaterialclassificationSearchRepository;
import kirgiz.stockandsalesmanagement.app.service.dto.MaterialclassificationDTO;
import kirgiz.stockandsalesmanagement.app.service.mapper.MaterialclassificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Materialclassification.
 */
@Service
@Transactional
public class MaterialclassificationService {

    private final Logger log = LoggerFactory.getLogger(MaterialclassificationService.class);

    private final MaterialclassificationRepository materialclassificationRepository;

    private final MaterialclassificationMapper materialclassificationMapper;

    private final MaterialclassificationSearchRepository materialclassificationSearchRepository;

    public MaterialclassificationService(MaterialclassificationRepository materialclassificationRepository, MaterialclassificationMapper materialclassificationMapper, MaterialclassificationSearchRepository materialclassificationSearchRepository) {
        this.materialclassificationRepository = materialclassificationRepository;
        this.materialclassificationMapper = materialclassificationMapper;
        this.materialclassificationSearchRepository = materialclassificationSearchRepository;
    }

    /**
     * Save a materialclassification.
     *
     * @param materialclassificationDTO the entity to save
     * @return the persisted entity
     */
    public MaterialclassificationDTO save(MaterialclassificationDTO materialclassificationDTO) {
        log.debug("Request to save Materialclassification : {}", materialclassificationDTO);
        Materialclassification materialclassification = materialclassificationMapper.toEntity(materialclassificationDTO);
        materialclassification = materialclassificationRepository.save(materialclassification);
        MaterialclassificationDTO result = materialclassificationMapper.toDto(materialclassification);
        materialclassificationSearchRepository.save(materialclassification);
        return result;
    }

    /**
     *  Get all the materialclassifications.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MaterialclassificationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Materialclassifications");
        return materialclassificationRepository.findAll(pageable)
            .map(materialclassificationMapper::toDto);
    }

    /**
     *  Get one materialclassification by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public MaterialclassificationDTO findOne(Long id) {
        log.debug("Request to get Materialclassification : {}", id);
        Materialclassification materialclassification = materialclassificationRepository.findOne(id);
        return materialclassificationMapper.toDto(materialclassification);
    }

    /**
     *  Delete the  materialclassification by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Materialclassification : {}", id);
        materialclassificationRepository.delete(id);
        materialclassificationSearchRepository.delete(id);
    }

    /**
     * Search for the materialclassification corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MaterialclassificationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Materialclassifications for query {}", query);
        Page<Materialclassification> result = materialclassificationSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(materialclassificationMapper::toDto);
    }
}
