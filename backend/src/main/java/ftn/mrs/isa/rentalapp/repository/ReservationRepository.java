package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.util.List;

public interface ReservationsRepository extends JpaRepository<Reservation,Integer> {

    @Query(value = "SELECT * FROM public.reservations WHERE end_date_time > :startDate and entity = :id ", nativeQuery = true)
    public List<Reservation> getReservationByEntity(@Param("startDate") LocalDate startDate, @Param("id") Integer id);

    @Query(value = "SELECT * FROM public.reservations WHERE end_date_time > :startDate and client = :id ", nativeQuery = true)
    public List<Reservation> getReservationByClient(@Param("startDate") LocalDate startDate, @Param("id") Integer id);

}
