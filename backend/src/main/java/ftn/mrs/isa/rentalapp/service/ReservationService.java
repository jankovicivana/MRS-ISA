package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> findAllHistoryByUser(Integer id) {return reservationRepository.findAllHistoryByUser(LocalDateTime.now(),id); }

    public List<Reservation> getFutureReservationByFishingInstructor(Integer id) {
        return reservationRepository.getFutureReservationByFishingInstructor(LocalDateTime.now(),id);
    }

    public List<Reservation> getReservationByFishingInstructor(Integer id) {
        return reservationRepository.getReservationByFishingInstructor(id);
    }

    public void saveAll(List<Reservation> reservations) {
        reservationRepository.saveAll(reservations);
    }
}
