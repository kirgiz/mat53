package kirgiz.stockandsalesmanagement.app.service;

import kirgiz.stockandsalesmanagement.app.domain.Material;
import kirgiz.stockandsalesmanagement.app.repository.MaterialRepository;
import kirgiz.stockandsalesmanagement.app.repository.search.MaterialSearchRepository;
import kirgiz.stockandsalesmanagement.app.service.dto.MaterialDTO;
import kirgiz.stockandsalesmanagement.app.service.mapper.MaterialMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Material.
 */
@Service
@Transactional
public class MaterialService {

    private final Logger log = LoggerFactory.getLogger(MaterialService.class);

    private final MaterialRepository materialRepository;

    private final MaterialMapper materialMapper;

    private final MaterialSearchRepository materialSearchRepository;

    public MaterialService(MaterialRepository materialRepository, MaterialMapper materialMapper, MaterialSearchRepository materialSearchRepository) {
        this.materialRepository = materialRepository;
        this.materialMapper = materialMapper;
        this.materialSearchRepository = materialSearchRepository;
    }

    /**
     * Save a material.
     *
     * @param materialDTO the entity to save
     * @return the persisted entity
     */
    public MaterialDTO save(MaterialDTO materialDTO) {
        log.debug("Request to save Material : {}", materialDTO);
        Material material = materialMapper.toEntity(materialDTO);
        material = materialRepository.save(material);
        MaterialDTO result = materialMapper.toDto(material);
        materialSearchRepository.save(material);
        return result;
    }

    /**
     *  Get all the materials.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MaterialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Materials");
        return materialRepository.findAll(pageable)
            .map(materialMapper::toDto);
    }

    /**
     *  Get one material by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public MaterialDTO findOne(Long id) {
        log.debug("Request to get Material : {}", id);
        Material material = materialRepository.findOne(id);
        return materialMapper.toDto(material);
    }

    /**
     *  Delete the  material by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Material : {}", id);
        materialRepository.delete(id);
        materialSearchRepository.delete(id);
    }

    /**
     * Search for the material corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MaterialDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Materials for query {}", query);
        Page<Material> result = materialSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(materialMapper::toDto);
    }
}
