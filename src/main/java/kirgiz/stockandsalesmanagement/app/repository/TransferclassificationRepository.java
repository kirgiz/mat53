package kirgiz.stockandsalesmanagement.app.repository;

import kirgiz.stockandsalesmanagement.app.domain.Transferclassification;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Transferclassification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransferclassificationRepository extends JpaRepository<Transferclassification, Long> {

}
