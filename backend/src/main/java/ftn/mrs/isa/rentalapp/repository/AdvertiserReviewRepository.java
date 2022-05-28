package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.reservation.Report;
import ftn.mrs.isa.rentalapp.model.user.AdvertiserReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdvertiserReviewRepository extends JpaRepository<AdvertiserReview,Integer> {

    @Query(value = "SELECT * FROM public.advertiser_reviews WHERE status = 1", nativeQuery = true)
    List<AdvertiserReview> findHoldOnReviews();

    @Query(value = "SELECT * FROM public.advertiser_reviews WHERE advertiser = :id and status = 0", nativeQuery = true)
    List<AdvertiserReview> findAllByAdvertiser( @Param("id") Integer id);

}
