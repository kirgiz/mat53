package kirgiz.stockandsalesmanagement.app.repository;

import kirgiz.stockandsalesmanagement.app.domain.Third;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Third entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThirdRepository extends JpaRepository<Third, Long> {
    @Query("select distinct third from Third third left join fetch third.addressthirds")
    List<Third> findAllWithEagerRelationships();

    @Query("select third from Third third left join fetch third.addressthirds where third.id =:id")
    Third findOneWithEagerRelationships(@Param("id") Long id);

}
