package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.user.FishingInstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FishingInstructorRepository extends JpaRepository<FishingInstructor,Integer> {


    @Query("SELECT b from FishingInstructor b where b.email=:string")
    FishingInstructor findByEmail(String string);

}
