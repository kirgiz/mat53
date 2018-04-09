package kirgiz.stockandsalesmanagement.app.service;

import kirgiz.stockandsalesmanagement.app.domain.Forexrates;
import kirgiz.stockandsalesmanagement.app.repository.ForexratesRepository;
import kirgiz.stockandsalesmanagement.app.repository.search.ForexratesSearchRepository;
import kirgiz.stockandsalesmanagement.app.service.dto.ForexratesDTO;
import kirgiz.stockandsalesmanagement.app.service.mapper.ForexratesMapper;
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
 * Service Implementation for managing Forexrates.
 */
@Service
@Transactional
public class ForexratesService {

    private final Logger log = LoggerFactory.getLogger(ForexratesService.class);

    private final ForexratesRepository forexratesRepository;

    private final ForexratesMapper forexratesMapper;

    private final ForexratesSearchRepository forexratesSearchRepository;

    public ForexratesService(ForexratesRepository forexratesRepository, ForexratesMapper forexratesMapper, ForexratesSearchRepository forexratesSearchRepository) {
        this.forexratesRepository = forexratesRepository;
        this.forexratesMapper = forexratesMapper;
        this.forexratesSearchRepository = forexratesSearchRepository;
    }

    /**
     * Save a forexrates.
     *
     * @param forexratesDTO the entity to save
     * @return the persisted entity
     */
    public ForexratesDTO save(ForexratesDTO forexratesDTO) {
        log.debug("Request to save Forexrates : {}", forexratesDTO);
        Forexrates forexrates = forexratesMapper.toEntity(forexratesDTO);
        forexrates = forexratesRepository.save(forexrates);
        ForexratesDTO result = forexratesMapper.toDto(forexrates);
        forexratesSearchRepository.save(forexrates);
        return result;
    }

    /**
     *  Get all the forexrates.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ForexratesDTO> findAll() {
        log.debug("Request to get all Forexrates");
        return forexratesRepository.findAll().stream()
            .map(forexratesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one forexrates by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ForexratesDTO findOne(Long id) {
        log.debug("Request to get Forexrates : {}", id);
        Forexrates forexrates = forexratesRepository.findOne(id);
        return forexratesMapper.toDto(forexrates);
    }

    /**
     *  Delete the  forexrates by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Forexrates : {}", id);
        forexratesRepository.delete(id);
        forexratesSearchRepository.delete(id);
    }

    /**
     * Search for the forexrates corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ForexratesDTO> search(String query) {
        log.debug("Request to search Forexrates for query {}", query);
        return StreamSupport
            .stream(forexratesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(forexratesMapper::toDto)
            .collect(Collectors.toList());
    }
}
