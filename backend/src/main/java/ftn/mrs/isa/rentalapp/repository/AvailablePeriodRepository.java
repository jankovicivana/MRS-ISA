package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.entity.AvailablePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface AvailablePeriodRepository extends  JpaRepository<AvailablePeriod,Integer> {

    @Query(value = "SELECT * FROM public.available_periods WHERE entity = :id and start_date_time < :start and end_date_time > :end", nativeQuery = true)
    public List<AvailablePeriod> getAvailable(Integer id, LocalDate start, LocalDate end);

    @Query(value = "SELECT * FROM public.available_periods WHERE entity = :id ", nativeQuery = true)
    public List<AvailablePeriod> getAvailableUserPeriod(Integer id);

}
