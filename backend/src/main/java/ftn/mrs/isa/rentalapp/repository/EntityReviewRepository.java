package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.entity.EntityReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EntityReviewRepository extends JpaRepository<EntityReview,Integer> {

    @Query(value = "SELECT * FROM public.entity_reviews WHERE status = 1", nativeQuery = true)
    List<EntityReview> findHoldOnReviews();
}
