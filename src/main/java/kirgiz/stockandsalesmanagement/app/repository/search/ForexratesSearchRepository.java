package kirgiz.stockandsalesmanagement.app.repository.search;

import kirgiz.stockandsalesmanagement.app.domain.Forexrates;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Forexrates entity.
 */
public interface ForexratesSearchRepository extends ElasticsearchRepository<Forexrates, Long> {
}
