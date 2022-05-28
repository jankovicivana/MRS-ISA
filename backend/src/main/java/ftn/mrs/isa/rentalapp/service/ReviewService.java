package ftn.mrs.isa.rentalapp.service;


import ftn.mrs.isa.rentalapp.model.entity.EntityReview;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import ftn.mrs.isa.rentalapp.model.user.Advertiser;
import ftn.mrs.isa.rentalapp.model.user.AdvertiserReview;
import ftn.mrs.isa.rentalapp.repository.AdvertiserReviewRepository;
import ftn.mrs.isa.rentalapp.repository.EntityReviewRepository;
import ftn.mrs.isa.rentalapp.repository.EntityTypeRepository;
import ftn.mrs.isa.rentalapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {


    @Autowired
    private EntityReviewRepository entityReviewRepository;

    @Autowired
    private AdvertiserReviewRepository advertiserReviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityTypeRepository entityTypeRepository;

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

    public void updateAverageGradeAdvertiser(Advertiser advertiser) {
        List<AdvertiserReview> reviews = advertiserReviewRepository.findAllByAdvertiser(advertiser.getId());
        double average_grade = 0.0;
        for (AdvertiserReview a:reviews){
            average_grade += a.getGrade();
        }
        if(reviews.size()!=0){
            average_grade = average_grade/reviews.size();
        }
        advertiser.setAverageGrade(average_grade);
        userRepository.save(advertiser);
    }

    public void updateAverageGradeEntity(Integer id) {
        List<EntityReview> entityReviews = entityReviewRepository.findAllByEntity(id);
        double average_grade = 0.0;
        for(EntityReview e:entityReviews){
            average_grade += e.getGrade();
        }
        if(entityReviews.size()!=0){
            average_grade = average_grade / entityReviews.size();
        }
        EntityType entityType = entityTypeRepository.findById(id).orElse(null);
        if (entityType!= null) {
            entityType.setAverageGrade(average_grade);
        }
        assert entityType != null;
        entityTypeRepository.save(entityType);

    }
}
