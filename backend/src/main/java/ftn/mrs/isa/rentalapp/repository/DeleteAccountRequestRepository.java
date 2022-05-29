package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.reservation.Report;
import ftn.mrs.isa.rentalapp.model.user.AccountDeleteRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeleteAccountRequestRepository extends JpaRepository<AccountDeleteRequest,Integer> {

    @Query(value = "SELECT a FROM AccountDeleteRequest a WHERE a.status = 1")
    List<AccountDeleteRequest> getOnHold();

}
