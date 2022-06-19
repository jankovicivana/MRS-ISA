package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.entity.Adventure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdventureRepository extends JpaRepository<Adventure,Integer> {

    @Query(value = "SELECT * from public.adventures inner join public.fishing_instructors ON adventures.fishing_instructor_id = fishing_instructors.id WHERE fishing_instructors.email = :email and adventures.deleted = false",nativeQuery = true)
    List<Adventure> findAllByOwnerEmail(@Param("email") String email);

}
