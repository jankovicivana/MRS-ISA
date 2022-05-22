package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.entity.Boat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoatRepository extends JpaRepository<Boat,Integer> {

    @Query(value = "SELECT * from public.boats inner join public.boat_owners ON boats.boat_owner = boat_owners.id WHERE boat_owners.email = :email",nativeQuery = true)
    List<Boat> findAllByOwnerEmail(@Param("email") String email);
}
