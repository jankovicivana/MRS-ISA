package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.reservation.Report;
import ftn.mrs.isa.rentalapp.model.user.Advertiser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report,Integer> {

    @Query(value = "SELECT * FROM public.reports WHERE penalty_status = 1", nativeQuery = true)
    List<Report> getReportsOnHold();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT e FROM Report e WHERE e.id = :id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    Report getReport(@Param("id") Integer id);
}
