package ftn.mrs.isa.rentalapp.service;


import ftn.mrs.isa.rentalapp.model.entity.EntityReview;
import ftn.mrs.isa.rentalapp.model.user.AdvertiserReview;
import ftn.mrs.isa.rentalapp.repository.AdvertiserReviewRepository;
import ftn.mrs.isa.rentalapp.repository.EntityReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {


    @Autowired
    private EntityReviewRepository entityReviewRepository;

    @Autowired
    private AdvertiserReviewRepository advertiserReviewRepository;


    public List<AdvertiserReview> getOnHoldAdvertiserRevisions() {
        return advertiserReviewRepository.findHoldOnReviews();
    }

    public List<EntityReview> getOnHoldEntityRevisions() {
        return entityReviewRepository.findHoldOnReviews();
    }

    public AdvertiserReview findAdvertiserReview(Integer id) {
        return advertiserReviewRepository.findById(id).orElse(null);
    }

    public EntityReview findEntityReview(Integer id) {
        return entityReviewRepository.findById(id).orElse(null);
    }

    public void saveAdvertiserReview(AdvertiserReview advertiserReview) {
        advertiserReviewRepository.save(advertiserReview);
    }

    public void saveEntityReview(EntityReview entityReview) {
        entityReviewRepository.save(entityReview);
    }
}
