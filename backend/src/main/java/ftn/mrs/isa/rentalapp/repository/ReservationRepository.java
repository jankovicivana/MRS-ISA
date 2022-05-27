package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {

    @Query(value = "SELECT * FROM public.reservations WHERE end_date_time > :startDate and entity = :id ", nativeQuery = true)
    public List<Reservation> getReservationByEntity(@Param("startDate") LocalDate startDate, @Param("id") Integer id);

    @Query(value = "SELECT * FROM public.reservations WHERE end_date_time > :startDate and client = :id ", nativeQuery = true)
    public List<Reservation> getReservationByClient(@Param("startDate") LocalDate startDate, @Param("id") Integer id);

    @Query(value = "SELECT * FROM public.reservations inner join public.adventures   ON  reservations.entity = adventures.id WHERE end_date_time < :startDate and adventures.fishing_instructor_id = :id  ", nativeQuery = true)
    public List<Reservation> findAllHistoryByUser(@Param("startDate") LocalDateTime startDate, @Param("id") Integer id);

    @Query(value = "SELECT * FROM public.reservations inner join public.adventures   ON  reservations.entity = adventures.id WHERE start_date_time > :startDate and adventures.fishing_instructor_id = :id  ", nativeQuery = true)
    public List<Reservation> getFutureReservationByFishingInstructor(@Param("startDate") LocalDateTime startDate, @Param("id") Integer id);

    @Query(value = "SELECT * FROM public.reservations inner join public.adventures   ON  reservations.entity = adventures.id WHERE  adventures.fishing_instructor_id = :id  ", nativeQuery = true)
    public List<Reservation> getReservationByFishingInstructor( @Param("id") Integer id);

    @Query(value = "SELECT * FROM public.reservations inner join public.boats   ON  reservations.entity = boats.id WHERE  start_date_time > :startDate and boats.boat_owner = :id  ", nativeQuery = true)
    List<Reservation> getFutureReservationByBoatOwner(@Param("startDate") LocalDateTime startDate, @Param("id") Integer id);

    @Query(value = "SELECT * FROM public.reservations inner join public.cottages   ON  reservations.entity = cottages.id WHERE  start_date_time > :startDate and cottages.cottage_owner_id  = :id  ", nativeQuery = true)
    List<Reservation> getFutureReservationByCottageOwner(@Param("startDate") LocalDateTime startDate, @Param("id") Integer id);

    @Query(value = "SELECT * FROM public.reservations inner join public.boats   ON  reservations.entity = boats.id WHERE  boats.boat_owner = :id  ", nativeQuery = true)
    List<Reservation> getReservationByBoatOwner(@Param("id") Integer id);
    @Query(value = "SELECT * FROM public.reservations inner join public.cottages   ON  reservations.entity = cottages.id WHERE end_date_time < :startDate and cottages.cottage_owner_id = :id  ", nativeQuery = true)
    public List<Reservation> findAllHistoryByCottageOwner(@Param("startDate") LocalDateTime startDate, @Param("id") Integer id);

    @Query(value = "SELECT * FROM public.reservations inner join public.cottages   ON  reservations.entity = cottages.id WHERE start_date_time > :startDate and cottages.cottage_owner_id = :id  ", nativeQuery = true)
    public List<Reservation> findAllUpcomingByCottageOwner(@Param("startDate") LocalDateTime startDate, @Param("id") Integer id);

    @Query(value = "SELECT * FROM public.reservations inner join public.cottages   ON  reservations.entity = cottages.id WHERE start_date_time < :startDate and end_date_time > :startDate and cottages.cottage_owner_id = :id  ", nativeQuery = true)
    public List<Reservation> findAllCurrentByCottageOwner(@Param("startDate") LocalDateTime startDate, @Param("id") Integer id);

    @Query(value = "SELECT * FROM public.reservations WHERE entity = :id and ((start_date_time < :start and end_date_time > :start) or (start_date_time < :end and end_date_time > :end))", nativeQuery = true)
    public List<Reservation> getReserved( @Param("id") Integer id,  @Param("start") LocalDateTime start,  @Param("end") LocalDateTime end);

}
