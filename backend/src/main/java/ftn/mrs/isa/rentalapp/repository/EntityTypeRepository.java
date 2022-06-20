package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface EntityTypeRepository extends JpaRepository<EntityType,Integer> {

   // @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT e FROM EntityType e WHERE e.id = :id")
    //@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    EntityType findOneLocked(@Param("id") Integer id);
}
