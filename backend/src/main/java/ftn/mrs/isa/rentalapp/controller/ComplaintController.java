package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.*;
import ftn.mrs.isa.rentalapp.model.entity.*;
import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.model.user.*;
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
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private EntityService entityService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ClientService clientService;

    @PostMapping(value = "/addComplaint")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<String> addComplaint(@RequestBody ComplaintDTO com, Principal principal){
        Client client = clientService.findByEmail(principal.getName());
        EntityType e = entityService.findOne(com.getEntityId());
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

        if(!kind.equals("Adventure") && !(com.getEntityComplaint().equals(""))){
            EntityComplaint entityComplaint = new EntityComplaint();
            entityComplaint.setEntity(e);
            entityComplaint.setStatus(RequestStatus.ON_HOLD);
            entityComplaint.setComplaint(com.getEntityComplaint());
            entityComplaint.setClient(client);
            entityComplaint.setAnswer("");
            complaintService.saveEntityComplaint(entityComplaint);
        }

        if(!com.getOwnerComplaint().equals("")){
            AdvertiserComplaint aComplaint = new AdvertiserComplaint();
            aComplaint.setComplaint(com.getOwnerComplaint());
            aComplaint.setStatus(RequestStatus.ON_HOLD);
            aComplaint.setClient(client);
            aComplaint.setAdvertiser(a);
            aComplaint.setAnswer("");
            complaintService.saveAdvertiserComplaint(aComplaint);
        }


        Reservation res = reservationService.getById(com.getReservationId());
        res.setIsComplained(true);  // oznaka da je complaint napisan
        reservationService.save(res);

        return new ResponseEntity<>("Successfully sent complaint.",HttpStatus.OK);
    }

    @GetMapping("/getOnHoldAdvertiserComplaint")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<AdvertiserComplaintDTO>> getOnHoldAdvertiserComplaint(Principal principal) {

        List<AdvertiserComplaint> complaints = complaintService.getOnHoldAdvertiserComplaints();
        List<AdvertiserComplaintDTO> complaintDTO = new ArrayList<>();
        for (AdvertiserComplaint d: complaints){
            complaintDTO.add(mapper.map(d,AdvertiserComplaintDTO.class));
        }
        return new ResponseEntity<>(complaintDTO, HttpStatus.CREATED);

    }


    @GetMapping("/getOnHoldEntityComplaint")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<EntityComplaintDTO>> getOnHoldEntityComplaint(Principal principal) {
        List<EntityComplaint> complaints = complaintService.getOnHoldEntityComplaints();
        List<EntityComplaintDTO> complaintDTOS= new ArrayList<>();
        for (EntityComplaint d: complaints){
            complaintDTOS.add(mapper.map(d, EntityComplaintDTO.class));
        }
        return new ResponseEntity<>(complaintDTOS, HttpStatus.CREATED);

    }

    @PostMapping(value = "/acceptAdvertiserComplaint")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> acceptAdvertiserComplaint(@RequestBody AdvertiserComplaintDTO advertiserComplaintDTO, Principal principal) throws InterruptedException, MessagingException {
        AdvertiserComplaint advertiserComplaint = mapper.map(advertiserComplaintDTO,AdvertiserComplaint.class);
        if(advertiserComplaint == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        advertiserComplaint.setStatus(RequestStatus.ACCEPTED);
        complaintService.saveAdvertiserComplaint(advertiserComplaint);
        emailService.sendComplaint(advertiserComplaint.getClient(), advertiserComplaint.getComplaint(),advertiserComplaint.getAnswer(),true);
        emailService.sendComplaint(advertiserComplaint.getAdvertiser(), advertiserComplaint.getComplaint(),advertiserComplaint.getAnswer(),true);
        return new ResponseEntity<>("Accepting is successful.",HttpStatus.OK);
    }

    @PostMapping(value = "/acceptEntityComplaint")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> acceptEntityComplaint(@RequestBody EntityComplaintDTO entityDTO, Principal principal) throws MessagingException, InterruptedException {
        EntityComplaint entityComplaint = mapper.map(entityDTO, EntityComplaint.class);
        if(entityComplaint == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        entityComplaint.setStatus(RequestStatus.ACCEPTED);
        complaintService.saveEntityComplaint(entityComplaint);
        emailService.sendComplaint(entityComplaint.getClient(), entityComplaint.getComplaint(),entityComplaint.getAnswer(),true);
        User u = userService.findUserByEntity(entityComplaint.getEntity());
        emailService.sendComplaint(u, entityComplaint.getComplaint(),entityComplaint.getAnswer(),true);
        return new ResponseEntity<>("Accepting is successful.",HttpStatus.OK);
    }

    @PostMapping(value = "/rejectAdvertiserComplaint")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> rejectAdvertiserComplaint(@RequestBody AdvertiserComplaintDTO advertiserComplaintDTO, Principal principal) throws MessagingException {
        AdvertiserComplaint advertiserComplaint = mapper.map(advertiserComplaintDTO,AdvertiserComplaint.class);
        if(advertiserComplaint == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        advertiserComplaint.setStatus(RequestStatus.REJECTED);
        complaintService.saveAdvertiserComplaint(advertiserComplaint);
        emailService.sendComplaint(advertiserComplaint.getClient(), advertiserComplaint.getComplaint(),advertiserComplaint.getAnswer(),false);
        emailService.sendComplaint(advertiserComplaint.getAdvertiser(), advertiserComplaint.getComplaint(),advertiserComplaint.getAnswer(),false);

        return new ResponseEntity<>("Rejecting is successful.",HttpStatus.OK);
    }

    @PostMapping(value = "/rejectEntityComplaint")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> rejectEntityComplaint(@RequestBody EntityComplaintDTO entityDTO, Principal principal) throws MessagingException {
        EntityComplaint entityComplaint = mapper.map(entityDTO, EntityComplaint.class);
        if(entityComplaint == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        entityComplaint.setStatus(RequestStatus.REJECTED);
        complaintService.saveEntityComplaint(entityComplaint);
        emailService.sendComplaint(entityComplaint.getClient(), entityComplaint.getComplaint(),entityComplaint.getAnswer(),false);
        User u = userService.findUserByEntity(entityComplaint.getEntity());
        emailService.sendComplaint(u, entityComplaint.getComplaint(),entityComplaint.getAnswer(),false);
        return new ResponseEntity<>("Rejecting is successful.",HttpStatus.OK);
    }

}
