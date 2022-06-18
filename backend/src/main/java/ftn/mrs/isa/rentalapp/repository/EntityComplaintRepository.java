package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.entity.EntityComplaint;
import ftn.mrs.isa.rentalapp.model.user.AdvertiserComplaint;
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
public interface EntityComplaintRepository extends JpaRepository< EntityComplaint,Integer> {
    @Query(value = "SELECT * FROM public.entity_complaints WHERE status = 1", nativeQuery = true)
    List<EntityComplaint> findHoldOnComplaints();


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT e FROM EntityComplaint e WHERE e.id = :id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    EntityComplaint getEntityComplaint(@Param("id") Integer id);

}
