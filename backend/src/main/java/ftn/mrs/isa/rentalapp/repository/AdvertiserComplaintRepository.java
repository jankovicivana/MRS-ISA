package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.user.AdvertiserComplaint;
import ftn.mrs.isa.rentalapp.model.user.AdvertiserReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertiserComplaintRepository extends JpaRepository< AdvertiserComplaint,Integer> {

    @Query(value = "SELECT * FROM public.advertiser_complaints WHERE status = 1", nativeQuery = true)
    List<AdvertiserComplaint> findHoldOnComplaints();


}
