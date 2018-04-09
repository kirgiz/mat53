package kirgiz.stockandsalesmanagement.app.service;

import kirgiz.stockandsalesmanagement.app.domain.Materialhistory;
import kirgiz.stockandsalesmanagement.app.repository.MaterialhistoryRepository;
import kirgiz.stockandsalesmanagement.app.repository.search.MaterialhistorySearchRepository;
import kirgiz.stockandsalesmanagement.app.service.dto.MaterialhistoryDTO;
import kirgiz.stockandsalesmanagement.app.service.mapper.MaterialhistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Materialhistory.
 */
@Service
@Transactional
public class MaterialhistoryService {

    private final Logger log = LoggerFactory.getLogger(MaterialhistoryService.class);

    private final MaterialhistoryRepository materialhistoryRepository;

    private final MaterialhistoryMapper materialhistoryMapper;

    private final MaterialhistorySearchRepository materialhistorySearchRepository;

    public MaterialhistoryService(MaterialhistoryRepository materialhistoryRepository, MaterialhistoryMapper materialhistoryMapper, MaterialhistorySearchRepository materialhistorySearchRepository) {
        this.materialhistoryRepository = materialhistoryRepository;
        this.materialhistoryMapper = materialhistoryMapper;
        this.materialhistorySearchRepository = materialhistorySearchRepository;
    }

    /**
     * Save a materialhistory.
     *
     * @param materialhistoryDTO the entity to save
     * @return the persisted entity
     */
    public MaterialhistoryDTO save(MaterialhistoryDTO materialhistoryDTO) {
        log.debug("Request to save Materialhistory : {}", materialhistoryDTO);
        Materialhistory materialhistory = materialhistoryMapper.toEntity(materialhistoryDTO);
        materialhistory = materialhistoryRepository.save(materialhistory);
        MaterialhistoryDTO result = materialhistoryMapper.toDto(materialhistory);
        materialhistorySearchRepository.save(materialhistory);
        return result;
    }

    /**
     *  Get all the materialhistories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MaterialhistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Materialhistories");
        return materialhistoryRepository.findAll(pageable)
            .map(materialhistoryMapper::toDto);
    }

    /**
     *  Get one materialhistory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public MaterialhistoryDTO findOne(Long id) {
        log.debug("Request to get Materialhistory : {}", id);
        Materialhistory materialhistory = materialhistoryRepository.findOneWithEagerRelationships(id);
        return materialhistoryMapper.toDto(materialhistory);
    }

    /**
     *  Delete the  materialhistory by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Materialhistory : {}", id);
        materialhistoryRepository.delete(id);
        materialhistorySearchRepository.delete(id);
    }

    /**
     * Search for the materialhistory corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MaterialhistoryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Materialhistories for query {}", query);
        Page<Materialhistory> result = materialhistorySearchRepository.search(queryStringQuery(query), pageable);
        return result.map(materialhistoryMapper::toDto);
    }
}
