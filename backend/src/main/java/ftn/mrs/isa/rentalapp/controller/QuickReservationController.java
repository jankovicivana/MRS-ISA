package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.AdventureDTO;
import ftn.mrs.isa.rentalapp.dto.QuickReservationDTO;
import ftn.mrs.isa.rentalapp.dto.ReservationDTO;
import ftn.mrs.isa.rentalapp.model.entity.Adventure;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import ftn.mrs.isa.rentalapp.model.entity.Subscription;
import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.model.user.FishingInstructor;
import ftn.mrs.isa.rentalapp.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/quickReservation")
public class QuickReservationController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private QuickReservationService quickReservationService;

    @Autowired
    private AdventureService adventureService;

    @Autowired
    private CottageService cottageService;

    @Autowired
    private BoatService boatService;

    @Autowired
    private FishingInstructorService fishingInstructorService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/addQuickReservation")
    @PreAuthorize("hasAnyRole('boatOwner','cottageOwner','fishingInstructor')")
    public ResponseEntity<QuickReservationDTO> addQuickReservation(@RequestBody QuickReservationDTO quickReservationDTO) throws Exception {
        EntityType entity = adventureService.findOne(quickReservationDTO.getEntId());
        if (entity == null){
            entity = cottageService.findOne(quickReservationDTO.getEntId());
        }
        if (entity == null){
            entity = boatService.findOne(quickReservationDTO.getEntId());
        }
        QuickReservation quickReservation = mapper.map(quickReservationDTO, QuickReservation.class);
        quickReservation.setIsReserved(false);
        quickReservation.setEntity(entity);
        quickReservation.setIsReserved(false);
        quickReservation.setReservation(null);
        System.out.print(quickReservation.getExpirationDateTime());
        quickReservationService.save(quickReservation);

        for(Subscription s: entity.getSubscriptions()){
            emailService.sendQuickReservationNotification(s.getClient().getEmail(), s.getClient().getName(), entity.getName());
        }

        return new ResponseEntity<>(mapper.map(quickReservation,QuickReservationDTO.class), HttpStatus.CREATED);

    }


    @GetMapping(value = "/findQuickReservationBy/getInstructor")
    @PreAuthorize("hasRole('fishingInstructor')")
    public ResponseEntity<List<QuickReservationDTO>> getAllReservationHistoryByUser(Principal principal){
        FishingInstructor instructor = fishingInstructorService.findByEmail(principal.getName());
        List<QuickReservation> reservations = quickReservationService.findQuickReservationByInstructor(instructor.getId());
        List<QuickReservationDTO> reservationsDTO = new ArrayList<>();
        for(QuickReservation c : reservations){
            QuickReservationDTO rt = mapper.map(c, QuickReservationDTO.class);
            reservationsDTO.add(rt);
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }


    @GetMapping(value = "/findQuickReservationBy/{id}")
    public ResponseEntity<List<QuickReservationDTO>> findQuickReservationOfEntity(@PathVariable Integer id){
        List<QuickReservation> reservations = quickReservationService.findQuickReservationByEntity(id);
        List<QuickReservationDTO> reservationsDTO = new ArrayList<>();
        for(QuickReservation c : reservations){
            QuickReservationDTO rt = mapper.map(c, QuickReservationDTO.class);
            reservationsDTO.add(rt);
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }


}
