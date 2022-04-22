package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.user.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {

}
