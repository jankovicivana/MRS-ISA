package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.user.Advertiser;
import ftn.mrs.isa.rentalapp.model.user.CottageOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CottageOwnerRepository extends JpaRepository<CottageOwner,Integer> {


    @Query("SELECT b from CottageOwner b where b.email=:string")
    CottageOwner findByEmail(String string);

}

