package kirgiz.stockandsalesmanagement.app.service;

import kirgiz.stockandsalesmanagement.app.domain.Civility;
import kirgiz.stockandsalesmanagement.app.repository.CivilityRepository;
import kirgiz.stockandsalesmanagement.app.repository.search.CivilitySearchRepository;
import kirgiz.stockandsalesmanagement.app.service.dto.CivilityDTO;
import kirgiz.stockandsalesmanagement.app.service.mapper.CivilityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Civility.
 */
@Service
@Transactional
public class CivilityService {

    private final Logger log = LoggerFactory.getLogger(CivilityService.class);

    private final CivilityRepository civilityRepository;

    private final CivilityMapper civilityMapper;

    private final CivilitySearchRepository civilitySearchRepository;

    public CivilityService(CivilityRepository civilityRepository, CivilityMapper civilityMapper, CivilitySearchRepository civilitySearchRepository) {
        this.civilityRepository = civilityRepository;
        this.civilityMapper = civilityMapper;
        this.civilitySearchRepository = civilitySearchRepository;
    }

    /**
     * Save a civility.
     *
     * @param civilityDTO the entity to save
     * @return the persisted entity
     */
    public CivilityDTO save(CivilityDTO civilityDTO) {
        log.debug("Request to save Civility : {}", civilityDTO);
        Civility civility = civilityMapper.toEntity(civilityDTO);
        civility = civilityRepository.save(civility);
        CivilityDTO result = civilityMapper.toDto(civility);
        civilitySearchRepository.save(civility);
        return result;
    }

    /**
     *  Get all the civilities.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CivilityDTO> findAll() {
        log.debug("Request to get all Civilities");
        return civilityRepository.findAll().stream()
            .map(civilityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one civility by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CivilityDTO findOne(Long id) {
        log.debug("Request to get Civility : {}", id);
        Civility civility = civilityRepository.findOne(id);
        return civilityMapper.toDto(civility);
    }

    /**
     *  Delete the  civility by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Civility : {}", id);
        civilityRepository.delete(id);
        civilitySearchRepository.delete(id);
    }

    /**
     * Search for the civility corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CivilityDTO> search(String query) {
        log.debug("Request to search Civilities for query {}", query);
        return StreamSupport
            .stream(civilitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(civilityMapper::toDto)
            .collect(Collectors.toList());
    }
}
