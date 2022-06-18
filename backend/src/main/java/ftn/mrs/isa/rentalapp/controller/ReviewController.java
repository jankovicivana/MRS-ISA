package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.*;
import ftn.mrs.isa.rentalapp.model.entity.*;
import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.model.user.Advertiser;
import ftn.mrs.isa.rentalapp.model.user.AdvertiserReview;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CottageOwnerService cottageOwnerService;

    @Autowired
    private BoatService boatService;

    @Autowired
    private AdventureService adventureService;

    @Autowired
    private EntityService entityService;

    @Autowired
    private ReservationService reservationService;

    @PostMapping(value = "/addReview")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<String> addReview(@RequestBody ReviewDTO r, Principal principal){

        Client client = clientService.findByEmail(principal.getName());
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
            reviewService.saveEntityReview(entityReview);
        }

        AdvertiserReview aReview = new AdvertiserReview();
        aReview.setReview(r.getOwnerReview());
        aReview.setGrade(r.getOwnerGrade());
        aReview.setStatus(RequestStatus.ON_HOLD);
        aReview.setClient(client);
        aReview.setAdvertiser(a);

        reviewService.saveAdvertiserReview(aReview);

        Reservation res = reservationService.getById(r.getReservationId());
        res.setIsReviewed(true);  // oznaka da je review napisan
        reservationService.save(res);

        return new ResponseEntity<>("Successfully sent review.",HttpStatus.OK);
    }


    @GetMapping("/getOnHoldAdvertiserReview")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<AdvertiserReviewDTO>> getOnHoldAdvertiserReview(Principal principal) {

        List<AdvertiserReview> revisions = reviewService.getOnHoldAdvertiserRevisions();
        List<AdvertiserReviewDTO> revisionDTO = new ArrayList<>();
        for (AdvertiserReview d: revisions){
            revisionDTO.add(mapper.map(d,AdvertiserReviewDTO.class));
        }
        return new ResponseEntity<>(revisionDTO, HttpStatus.CREATED);

    }

    @GetMapping("/getOnHoldEntityReview")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<EntityReviewDTO>> getOnHoldEntityReview(Principal principal) {
        List<EntityReview> revisions = reviewService.getOnHoldEntityRevisions();
        List<EntityReviewDTO> revisionDTO = new ArrayList<>();
        for (EntityReview d: revisions){
            revisionDTO.add(mapper.map(d,EntityReviewDTO.class));
        }
        return new ResponseEntity<>(revisionDTO, HttpStatus.CREATED);

    }

    @GetMapping(value = "/acceptAdvertiserReview/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> acceptAdvertiserReview(@PathVariable Integer id, Principal principal) throws InterruptedException, MessagingException {
        AdvertiserReview advertiserReview = reviewService.findAdvertiserReview(id);
        if(advertiserReview == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        advertiserReview.setStatus(RequestStatus.ACCEPTED);
        reviewService.saveAdvertiserReview(advertiserReview);
        reviewService.updateAverageGradeAdvertiser(advertiserReview.getAdvertiser());
        emailService.sendNotificationAsync(advertiserReview.getClient(), advertiserReview.getAdvertiser().getName()+" "+advertiserReview.getAdvertiser().getSurname());
        return new ResponseEntity<>("Accepting is successful.",HttpStatus.OK);
    }

    @GetMapping(value = "/acceptEntityReview/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> acceptEntityReview(@PathVariable Integer id, Principal principal) throws MessagingException, InterruptedException {
        EntityReview entityReview = reviewService.findEntityReview(id);
        if(entityReview == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        entityReview.setStatus(RequestStatus.ACCEPTED);
        reviewService.saveEntityReview(entityReview);
        reviewService.updateAverageGradeEntity(entityReview.getEntity().getId());
        emailService.sendNotificationAsync(entityReview.getClient(), entityReview.getEntity().getName());
        return new ResponseEntity<>("Accepting is successful.",HttpStatus.OK);
    }

    @GetMapping(value = "/rejectAdvertiserReview/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> rejectAdvertiserReview(@PathVariable Integer id, Principal principal){
        AdvertiserReview advertiserReview = reviewService.findAdvertiserReview(id);
        if(advertiserReview == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        advertiserReview.setStatus(RequestStatus.REJECTED);
        reviewService.saveAdvertiserReview(advertiserReview);
        return new ResponseEntity<>("Rejecting is successful.",HttpStatus.OK);
    }

    @GetMapping(value = "/rejectEntityReview/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> rejectEntityReview(@PathVariable Integer id, Principal principal){
        EntityReview entityReview = reviewService.findEntityReview(id);
        if(entityReview == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        entityReview.setStatus(RequestStatus.REJECTED);
        reviewService.saveEntityReview(entityReview);
        return new ResponseEntity<>("Rejecting is successful.",HttpStatus.OK);
    }

}
