package kirgiz.stockandsalesmanagement.app.repository.search;

import kirgiz.stockandsalesmanagement.app.domain.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the User entity.
 */
public interface UserSearchRepository extends ElasticsearchRepository<User, Long> {
}
