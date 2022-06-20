package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.entity.Adventure;
import ftn.mrs.isa.rentalapp.model.entity.Cottage;
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
public interface AdventureRepository extends JpaRepository<Adventure,Integer> {

    @Query(value = "SELECT * from public.adventures inner join public.fishing_instructors ON adventures.fishing_instructor_id = fishing_instructors.id WHERE fishing_instructors.email = :email and adventures.deleted = false",nativeQuery = true)
    List<Adventure> findAllByOwnerEmail(@Param("email") String email);

    @Query(value = "SELECT * from public.adventures inner join public.fishing_instructors ON adventures.fishing_instructor_id = fishing_instructors.id WHERE fishing_instructors.id = :id and adventures.deleted = false",nativeQuery = true)
    List<Adventure> findAllByOwnerId(@Param("id") Integer id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT a FROM Adventure a WHERE a.id = :id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    Adventure findOneLocked(@Param("id") Integer id);
}
