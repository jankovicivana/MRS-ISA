package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.user.Advertiser;
import ftn.mrs.isa.rentalapp.model.user.BoatOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoatOwnerRepository extends JpaRepository<BoatOwner,Integer> {

    @Query("SELECT b from BoatOwner b where b.email=:string")
    BoatOwner findByEmail(String string);


}
