package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Integer> {
}
