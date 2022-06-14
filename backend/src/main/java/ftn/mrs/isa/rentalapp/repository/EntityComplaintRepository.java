package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.entity.EntityComplaint;
import ftn.mrs.isa.rentalapp.model.user.AdvertiserComplaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntityComplaintRepository extends JpaRepository< EntityComplaint,Integer> {
    @Query(value = "SELECT * FROM public.entity_complaints WHERE status = 1", nativeQuery = true)
    List<EntityComplaint> findHoldOnComplaints();


}
