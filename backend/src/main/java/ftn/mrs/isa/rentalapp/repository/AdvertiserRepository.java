package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.entity.EntityComplaint;
import ftn.mrs.isa.rentalapp.model.reservation.Report;
import ftn.mrs.isa.rentalapp.model.user.Advertiser;
import ftn.mrs.isa.rentalapp.model.user.User;
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
public interface AdvertiserRepository extends JpaRepository<Advertiser,Integer> {


    @Query(value = "SELECT a FROM Advertiser a WHERE a.registrationStatus = 1")
    List<Advertiser> getOnHold();

    @Query(value = "SELECT e FROM Advertiser e WHERE e.id = :id")
    Advertiser getAdvertiser(@Param("id") Integer id);

}
