package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.*;
import ftn.mrs.isa.rentalapp.model.entity.Adventure;
import ftn.mrs.isa.rentalapp.model.entity.Boat;
import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.model.entity.EntityKind;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import ftn.mrs.isa.rentalapp.model.entity.*;
import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.model.user.BoatOwner;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.model.user.CottageOwner;
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
@RequestMapping("api/reservation")
public class ReservationController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private AdventureService adventureService;

    @Autowired
    private CottageService cottageService;

    @Autowired
    private BoatOwnerService boatOwnerService;

    @Autowired
    private BoatService boatService;

    @Autowired
    private CottageOwnerService cottageOwnerService;

    @Autowired
    private FishingInstructorService fishingInstructorService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private QuickReservationService quickReservationService;

    @Autowired
    private EntityService entityService;

    @GetMapping(value = "/findHistoryByUser/getInstructor")
    @PreAuthorize("hasRole('fishingInstructor')")
    public ResponseEntity<List<ReservationDTO>> getAllReservationHistoryByUser( Principal principal){
        FishingInstructor instructor = fishingInstructorService.findByEmail(principal.getName());
        List<Reservation> reservations = reservationService.findAllHistoryByUser(instructor.getId());
        return getListInstructorReservation(reservations);
    }

    @GetMapping(value = "/findAllByUser/getInstructor")
    @PreAuthorize("hasRole('fishingInstructor')")
    public ResponseEntity<List<ReservationDTO>> getAllReservationByUser( Principal principal){
        FishingInstructor instructor = fishingInstructorService.findByEmail(principal.getName());
        List<Reservation> reservations = reservationService.findAllByUser(instructor.getId());
        return getListInstructorReservation(reservations);
    }

    private ResponseEntity<List<ReservationDTO>> getListInstructorReservation(List<Reservation> reservations) {
        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for(Reservation c : reservations){
            ReservationDTO rt = mapper.map(c, ReservationDTO.class);
            Adventure a = adventureService.findOne(c.getEntity().getId());
            rt.setAdventure(mapper.map(a, AdventureDTO.class));
            reservationsDTO.add(rt);
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/findHistoryByCottageOwner")
    @PreAuthorize("hasRole('cottageOwner')")
    public ResponseEntity<List<ReservationDTO>> getAllReservationHistoryByCottageOwner(Principal principal){
        CottageOwner owner = cottageOwnerService.findByEmail(principal.getName());
        List<Reservation> reservations = reservationService.findAllHistoryByCottageOwner(owner.getId());
        return getListResponseEntity(reservations);
    }

    @GetMapping(value = "/findUpcomingByCottageOwner/{id}")
    @PreAuthorize("hasRole('cottageOwner')")
    public ResponseEntity<List<ReservationDTO>> getAllUpcomingReservationByCottageOwner(@PathVariable Integer id){
        List<Reservation> reservations = reservationService.findAllUpcomingByCottageOwner(id);
        return getListResponseEntity(reservations);
    }

    @GetMapping(value = "/findCurrentByCottageOwner/{id}")
    @PreAuthorize("hasRole('cottageOwner')")
    public ResponseEntity<List<ReservationDTO>> getAllCurrentReservationByCottageOwner(@PathVariable Integer id){
        List<Reservation> reservations = reservationService.findAllCurrentByCottageOwner(id);
        return getListResponseEntity(reservations);
    }

    @GetMapping(value = "/findAllOfCottage/{id}")
    public ResponseEntity<List<ReservationDTO>> findAllOfCottage(@PathVariable Integer id){
        List<Reservation> reservations = reservationService.findAllByEntity(id);
        return getListResponseEntity(reservations);
    }

    @GetMapping(value = "/findAllOfBoat/{id}")
    public ResponseEntity<List<ReservationDTO>> findAllOfBoat(@PathVariable Integer id){
        List<Reservation> reservations = reservationService.findAllByEntity(id);
        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for(Reservation c : reservations){
            ReservationDTO rt = mapper.map(c, ReservationDTO.class);
            Boat boat = boatService.findOne(c.getEntity().getId());
            rt.setCottage(mapper.map(boat, CottageDTO.class));
            reservationsDTO.add(rt);
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/findHistoryByBoatOwner")
    @PreAuthorize("hasRole('boatOwner')")
    public ResponseEntity<List<ReservationDTO>> getAllReservationHistoryByBoatOwner(Principal principal){
        BoatOwner owner = boatOwnerService.findByEmail(principal.getName());
        List<Reservation> reservations = reservationService.getHistoryReservationByBoatOwner(owner.getId());
        return getListResponseBoat(reservations);
    }



    @GetMapping(value = "/findUpcomingByBoatOwner")
    @PreAuthorize("hasRole('boatOwner')")
    public ResponseEntity<List<ReservationDTO>> getAllUpcomingReservationByBoatOwner(Principal principal){
        BoatOwner owner = boatOwnerService.findByEmail(principal.getName());
        List<Reservation> reservations = reservationService.getFutureReservationByBoatOwner(owner.getId());
        return getListResponseBoat(reservations);
    }

    @GetMapping(value = "/findCurrentByBoatOwner")
    @PreAuthorize("hasRole('boatOwner')")
    public ResponseEntity<List<ReservationDTO>> getAllCurrentReservationByCottageOwner(Principal principal) {
        BoatOwner owner = boatOwnerService.findByEmail(principal.getName());
        List<Reservation> reservations = reservationService.getCurrentReservationByBoatOwner(owner.getId());
        return getListResponseBoat(reservations);
    }

    private ResponseEntity<List<ReservationDTO>> getListResponseEntity(List<Reservation> reservations) {
        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for(Reservation c : reservations){
            ReservationDTO rt = mapper.map(c, ReservationDTO.class);
            Cottage cottage = cottageService.findOne(c.getEntity().getId());
            rt.setCottage(mapper.map(cottage, CottageDTO.class));
            reservationsDTO.add(rt);
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }

    private ResponseEntity<List<ReservationDTO>> getListResponseBoat(List<Reservation> reservations) {
        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for(Reservation c : reservations){
            ReservationDTO rt = mapper.map(c, ReservationDTO.class);
            Boat boat = boatService.findOne(c.getEntity().getId());
            rt.setBoat(mapper.map(boat, BoatDTO.class));
            reservationsDTO.add(rt);
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/findHistoryByClient")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<List<ReservationDTO>> getHistoryByClient(Principal principal){
        Client client = clientService.findByEmail(principal.getName());
        List<Reservation> reservationList = reservationService.getHistoryByClient(client.getId());

        // ovo moze kasnije da se izvuce u posebnu metodu
        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for(Reservation r: reservationList){
            ReservationDTO dto = mapper.map(r, ReservationDTO.class);
            dto.getEntity().setType(EntityKind.toString(r.getEntity().getKind()));
            reservationsDTO.add(dto);
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/findUpcomingByClient")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<List<ReservationDTO>> getUpcomingByClient(Principal principal){
        Client client = clientService.findByEmail(principal.getName());
        List<Reservation> reservationList = reservationService.getUpcomingByClient(client.getId());
        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for(Reservation r: reservationList){
            ReservationDTO dto = mapper.map(r, ReservationDTO.class);
            dto.getEntity().setType(EntityKind.toString(r.getEntity().getKind()));
            reservationsDTO.add(dto);
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }

    @PutMapping("/makeReservationFromQuick/{id}")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<String> makeReservationFromQuick(@PathVariable(value = "id") Integer id,Principal principal){
        System.out.println(id+"****************************************");
        Client c = clientService.findByEmail(principal.getName());
        QuickReservation quickReservation = quickReservationService.findOne(id);
        quickReservation.setIsReserved(true);
        Reservation r = new Reservation();
        r.setQuickReservation(quickReservation);
        r.setEntity(quickReservation.getEntity());
        r.setClient(c);
        r.setPrice(quickReservation.getDiscountedPrice());
        r.setStartDateTime(quickReservation.getStartDateTime());
        r.setEndDateTime(quickReservation.getEndDateTime());
        r.setIsCanceled(false);
        r.setPersonNum(quickReservation.getMaxPersonNum());
        r.setAdvertiserProfit(0.0);
        r.setSystemProfit(0.0);
        reservationService.save(r);
        quickReservationService.save(quickReservation);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
