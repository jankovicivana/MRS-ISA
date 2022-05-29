package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.reservation.Report;
import ftn.mrs.isa.rentalapp.model.user.Advertiser;
import ftn.mrs.isa.rentalapp.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertiserRepository extends JpaRepository<Advertiser,Integer> {


    @Query(value = "SELECT a FROM Advertiser a WHERE a.registrationStatus = 1")
    List<Advertiser> getOnHold();

}
