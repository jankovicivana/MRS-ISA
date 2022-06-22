package ftn.mrs.isa.rentalapp.service;


import ftn.mrs.isa.rentalapp.dto.ReviewDTO;
import ftn.mrs.isa.rentalapp.model.entity.*;
import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.model.user.Advertiser;
import ftn.mrs.isa.rentalapp.model.user.AdvertiserReview;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReviewService {


    @Autowired
    private EntityReviewRepository entityReviewRepository;

    @Autowired
    private AdvertiserReviewRepository advertiserReviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityTypeRepository entityTypeRepository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EntityService entityService;

    @Autowired
    private ReservationRepository reservationRepository;

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

    public ResponseEntity<String> addReview(ReviewDTO r, String name) {
        try{
            Client client = clientService.findByEmail(name);
            EntityType e = entityService.findOne(r.getEntityId());
            Advertiser a = null;
            String kind = EntityKind.toString(e.getKind());

            if(kind.equals("Cottage")){
                Cottage c = (Cottage)e;
                a = c.getCottageOwner();
            } else if(kind.equals("Boat")){
                Boat b = (Boat)e;
                a = b.getBoatOwner();
            } else{
                Adventure adv = (Adventure)e;
                a = adv.getFishingInstructor();
            }

            if(!kind.equals("Adventure")){
                EntityReview entityReview = new EntityReview();
                entityReview.setEntity(e);
                entityReview.setStatus(RequestStatus.ON_HOLD);
                entityReview.setReview(r.getEntityReview());
                entityReview.setGrade(r.getEntityGrade());
                entityReview.setClient(client);
                saveEntityReview(entityReview);
            }

            AdvertiserReview aReview = new AdvertiserReview();
            aReview.setReview(r.getOwnerReview());
            aReview.setGrade(r.getOwnerGrade());
            aReview.setStatus(RequestStatus.ON_HOLD);
            aReview.setClient(client);
            aReview.setAdvertiser(a);

            saveAdvertiserReview(aReview);

            Reservation res = reservationRepository.getByIdLocked(r.getReservationId());
            System.out.println("Id rezervacije " + r.getReservationId());
            System.out.println("Rezervacija  " + res.getId());
            // provjera
            if(res.getIsReviewed() != null && res.getIsReviewed()){
                return new ResponseEntity<>("Review already sent", HttpStatus.CONFLICT);
            }

            res.setIsReviewed(true);  // oznaka da je review napisan
            reservationService.save(res);

            return new ResponseEntity<>("Successfully sent review.", HttpStatus.OK);
        }catch(PessimisticLockingFailureException e){
            return new ResponseEntity<>("Review already sent", HttpStatus.CONFLICT);
        }

    }
}
