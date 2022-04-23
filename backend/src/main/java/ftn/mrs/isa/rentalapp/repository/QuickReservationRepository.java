package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuickReservationRepository extends JpaRepository<QuickReservation,Integer> {
}
