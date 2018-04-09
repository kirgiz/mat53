package kirgiz.stockandsalesmanagement.app.repository;

import kirgiz.stockandsalesmanagement.app.domain.Forexrates;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Forexrates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ForexratesRepository extends JpaRepository<Forexrates, Long> {

}
