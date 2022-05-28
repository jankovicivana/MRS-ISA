package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.repository.ReservationRepository;
import org.hibernate.Session;
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

    public List<Reservation> getFutureReservationByBoatOwner(Integer id) {
        return reservationRepository.getFutureReservationByBoatOwner(LocalDateTime.now(),id);
    }
    public List<Reservation> getFutureReservationByCottageOwner(Integer id) {
        return reservationRepository.getFutureReservationByCottageOwner(LocalDateTime.now(),id);
    }

    public List<Reservation> getReservationByBoatOwner(Integer id) {
        return reservationRepository.getReservationByBoatOwner(id);
    }
    public List<Reservation> findAllHistoryByCottageOwner(Integer id) {return reservationRepository.findAllHistoryByCottageOwner(LocalDateTime.now(),id); }

    public List<Reservation> findAllUpcomingByCottageOwner(Integer id) {return reservationRepository.findAllUpcomingByCottageOwner(LocalDateTime.now(),id); }

    public List<Reservation> findAllCurrentByCottageOwner(Integer id) {return reservationRepository.findAllCurrentByCottageOwner(LocalDateTime.now(),id); }

    public List<Reservation> findAllByEntity(Integer id) {return reservationRepository.findAllByEntity(id); }

    public boolean isReserved(Integer id, LocalDateTime start, LocalDateTime end){
        List<Reservation> reservations = reservationRepository.getReserved(id, start, end);
        System.out.println("Reservations: " + reservations);
        return !reservations.isEmpty();
    }

    public List<Reservation> findAllByUser(Integer id) {
        return  reservationRepository.findAllByUser(id);
    }

    public List<Reservation> getHistoryByClient(Integer id) {
        return reservationRepository.findAllHistoryByClient(LocalDateTime.now(),id);
    }

    public List<Reservation> getUpcomingByClient(Integer id) {
        return reservationRepository.findAllUpcomingByClient(LocalDateTime.now(),id);
    }

    public Reservation getById(Integer resId) {
        return reservationRepository.getById(resId);
    }

    public void cancelReservation(Integer id) {
        reservationRepository.cancel(id);
    }
}
