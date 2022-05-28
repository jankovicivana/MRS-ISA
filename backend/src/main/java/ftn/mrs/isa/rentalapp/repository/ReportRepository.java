package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.reservation.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Integer> {

    @Query(value = "SELECT * FROM public.reports WHERE penalty_status = 1", nativeQuery = true)
    List<Report> getReportsOnHold();

}
