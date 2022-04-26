package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.user.CottageOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CottageOwnerRepository extends JpaRepository<CottageOwner,Integer> {
}
