package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

public interface CottageRepository extends JpaRepository<Cottage,Integer> {

    @Query(value = "SELECT * from public.cottages inner join public.cottage_owners ON cottages.cottage_owner_id = cottage_owners.id WHERE cottage_owners.email = :email and cottages.deleted = false",nativeQuery = true)
    List<Cottage> findAllByOwnerEmail(@Param("email") String email);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT c FROM Cottage c WHERE c.id = :id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    Cottage findOneLocked(@Param("id") Integer id);
}