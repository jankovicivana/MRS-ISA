package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CottageRepository extends JpaRepository<Cottage,Integer> {

    @Query(value = "SELECT * from public.cottages inner join public.cottage_owners ON cottages.cottage_owner_id = cottage_owners.id WHERE cottage_owners.email = :email and cottages.deleted = false",nativeQuery = true)
    List<Cottage> findAllByOwnerEmail(@Param("email") String email);

}