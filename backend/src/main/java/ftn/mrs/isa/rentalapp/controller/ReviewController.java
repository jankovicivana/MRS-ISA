package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.AdvertiserReviewDTO;
import ftn.mrs.isa.rentalapp.dto.EntityReviewDTO;
import ftn.mrs.isa.rentalapp.model.entity.EntityReview;
import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import ftn.mrs.isa.rentalapp.model.user.AdvertiserReview;
import ftn.mrs.isa.rentalapp.service.EmailService;
import ftn.mrs.isa.rentalapp.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private ModelMapper mapper;

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
        System.out.print("pogodjenooo");
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
        //azuuriraj prosjecnu ocjenu
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
        //azuuriraj prosjecnu ocjenu
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
