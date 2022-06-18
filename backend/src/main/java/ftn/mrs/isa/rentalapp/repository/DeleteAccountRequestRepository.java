package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.reservation.Report;
import ftn.mrs.isa.rentalapp.model.user.AccountDeleteRequest;
import ftn.mrs.isa.rentalapp.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

public interface DeleteAccountRequestRepository extends JpaRepository<AccountDeleteRequest,Integer> {

    @Query(value = "SELECT a FROM AccountDeleteRequest a WHERE a.status = 1")
    List<AccountDeleteRequest> getOnHold();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT a FROM AccountDeleteRequest a WHERE a.id = :id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    AccountDeleteRequest getDeletionRequest(@Param("id") Integer id);

}

