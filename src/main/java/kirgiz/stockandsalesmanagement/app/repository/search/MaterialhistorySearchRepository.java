package kirgiz.stockandsalesmanagement.app.repository.search;

import kirgiz.stockandsalesmanagement.app.domain.Materialhistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Materialhistory entity.
 */
public interface MaterialhistorySearchRepository extends ElasticsearchRepository<Materialhistory, Long> {
}
