package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.time.LocalDateTime;
import java.util.List;

public interface QuickReservationRepository extends JpaRepository<QuickReservation,Integer> {

    @Query(value = "SELECT * FROM public.quick_reservations inner join public.adventures   ON  quick_reservations.entity = adventures.id WHERE adventures.fishing_instructor_id = :id  ", nativeQuery = true)
    public List<QuickReservation> findQuickReservationByInstructor( @Param("id") Integer id);

    @Query(value = "SELECT * FROM public.quick_reservations  WHERE quick_reservations.entity = :id  ", nativeQuery = true)
    public List<QuickReservation> findQuickReservationByEntity( @Param("id") Integer id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT q FROM QuickReservation q WHERE q.id = :id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    QuickReservation findOneLocked(@Param("id") Integer id);
}
