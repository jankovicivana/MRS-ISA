package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.user.AdvertiserComplaint;
import ftn.mrs.isa.rentalapp.model.user.AdvertiserReview;
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
public interface AdvertiserComplaintRepository extends JpaRepository< AdvertiserComplaint,Integer> {

    @Query(value = "SELECT * FROM public.advertiser_complaints WHERE status = 1", nativeQuery = true)
    List<AdvertiserComplaint> findHoldOnComplaints();


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT a FROM AdvertiserComplaint a WHERE a.id = :id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    AdvertiserComplaint getAdvertiserComplaint(@Param("id") Integer id);


}
