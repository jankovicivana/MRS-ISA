package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.entity.Boat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoatRepository extends JpaRepository<Boat,Integer> {
}
