package kirgiz.stockandsalesmanagement.app.repository;

import kirgiz.stockandsalesmanagement.app.domain.Materialhistory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Materialhistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterialhistoryRepository extends JpaRepository<Materialhistory, Long> {
    @Query("select distinct materialhistory from Materialhistory materialhistory left join fetch materialhistory.itemTransfereds")
    List<Materialhistory> findAllWithEagerRelationships();

    @Query("select materialhistory from Materialhistory materialhistory left join fetch materialhistory.itemTransfereds where materialhistory.id =:id")
    Materialhistory findOneWithEagerRelationships(@Param("id") Long id);

}
