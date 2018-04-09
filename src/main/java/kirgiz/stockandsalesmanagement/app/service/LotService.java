package kirgiz.stockandsalesmanagement.app.service;

import kirgiz.stockandsalesmanagement.app.domain.Lot;
import kirgiz.stockandsalesmanagement.app.repository.LotRepository;
import kirgiz.stockandsalesmanagement.app.repository.search.LotSearchRepository;
import kirgiz.stockandsalesmanagement.app.service.dto.LotDTO;
import kirgiz.stockandsalesmanagement.app.service.mapper.LotMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Lot.
 */
@Service
@Transactional
public class LotService {

    private final Logger log = LoggerFactory.getLogger(LotService.class);

    private final LotRepository lotRepository;

    private final LotMapper lotMapper;

    private final LotSearchRepository lotSearchRepository;

    public LotService(LotRepository lotRepository, LotMapper lotMapper, LotSearchRepository lotSearchRepository) {
        this.lotRepository = lotRepository;
        this.lotMapper = lotMapper;
        this.lotSearchRepository = lotSearchRepository;
    }

    /**
     * Save a lot.
     *
     * @param lotDTO the entity to save
     * @return the persisted entity
     */
    public LotDTO save(LotDTO lotDTO) {
        log.debug("Request to save Lot : {}", lotDTO);
        Lot lot = lotMapper.toEntity(lotDTO);
        lot = lotRepository.save(lot);
        LotDTO result = lotMapper.toDto(lot);
        lotSearchRepository.save(lot);
        return result;
    }

    /**
     *  Get all the lots.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LotDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lots");
        return lotRepository.findAll(pageable)
            .map(lotMapper::toDto);
    }

    /**
     *  Get one lot by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public LotDTO findOne(Long id) {
        log.debug("Request to get Lot : {}", id);
        Lot lot = lotRepository.findOne(id);
        return lotMapper.toDto(lot);
    }

    /**
     *  Delete the  lot by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Lot : {}", id);
        lotRepository.delete(id);
        lotSearchRepository.delete(id);
    }

    /**
     * Search for the lot corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LotDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Lots for query {}", query);
        Page<Lot> result = lotSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(lotMapper::toDto);
    }
}
