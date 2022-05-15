package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> findAllHistoryByUser(Integer id) {return reservationRepository.findAllHistoryByUser(LocalDateTime.now(),id); }

    public List<Reservation> findAllHistoryByCottageOwner(Integer id) {return reservationRepository.findAllHistoryByCottageOwner(LocalDateTime.now(),id); }

    public List<Reservation> findAllUpcomingByCottageOwner(Integer id) {return reservationRepository.findAllUpcomingByCottageOwner(LocalDateTime.now(),id); }

    public List<Reservation> findAllCurrentByCottageOwner(Integer id) {return reservationRepository.findAllCurrentByCottageOwner(LocalDateTime.now(),id); }

}
