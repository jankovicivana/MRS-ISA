package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CottageRepository extends JpaRepository<Cottage,Integer> {

}