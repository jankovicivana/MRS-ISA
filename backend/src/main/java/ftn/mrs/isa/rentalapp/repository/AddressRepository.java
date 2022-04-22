package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.user.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
