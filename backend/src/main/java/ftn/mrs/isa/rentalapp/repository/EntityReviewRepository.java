package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.entity.EntityReview;
import ftn.mrs.isa.rentalapp.model.user.AdvertiserReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EntityReviewRepository extends JpaRepository<EntityReview,Integer> {

    @Query(value = "SELECT * FROM public.entity_reviews WHERE status = 1", nativeQuery = true)
    List<EntityReview> findHoldOnReviews();

    @Query(value = "SELECT * FROM public.entity_reviews WHERE entity = :id and status = 0", nativeQuery = true)
    List<EntityReview> findAllByEntity(@Param("id") Integer id);

}
