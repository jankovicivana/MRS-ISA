package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.entity.AvailablePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface AvailablePeriodRepository extends  JpaRepository<AvailablePeriod,Integer> {

    @Query(value = "SELECT * FROM public.available_periods WHERE entity = :id and start_date_time < :start and end_date_time > :end", nativeQuery = true)
    public List<AvailablePeriod> getAvailable(@Param("id") Integer id, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(value = "SELECT * FROM public.available_periods WHERE available_periods.fishing_instructor = :id and start_date_time < :start and end_date_time > :end", nativeQuery = true)
    public List<AvailablePeriod> getAvailableInstructor(@Param("id") Integer id, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(value = "SELECT * FROM public.available_periods WHERE fishing_instructor = :id ", nativeQuery = true)
    public List<AvailablePeriod> getAvailablePeriodFishingInstructor(Integer id);

    @Query(value = "SELECT * FROM public.available_periods WHERE entity = :id ", nativeQuery = true)
    public List<AvailablePeriod> getAvailablePeriodEntity(Integer id);
}
