package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.*;
import ftn.mrs.isa.rentalapp.model.entity.Adventure;
import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.model.entity.EntityKind;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private CottageOwnerService cottageOwnerService;

    @Autowired
    private FishingInstructorService fishingInstructorService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EntityService entityService;

    @GetMapping(value = "/findHistoryByUser/getInstructor")
    @PreAuthorize("hasRole('fishingInstructor')")
    public ResponseEntity<List<ReservationDTO>> getAllReservationHistoryByUser( Principal principal){
        FishingInstructor instructor = fishingInstructorService.findByEmail(principal.getName());
        List<Reservation> reservations = reservationService.findAllHistoryByUser(instructor.getId());
        System.out.print("-------------------------------->"+reservations.size());
        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for(Reservation c : reservations){
            ReservationDTO rt = mapper.map(c, ReservationDTO.class);
            Adventure a = adventureService.findOne(c.getEntity().getId());
            rt.setAdventure(mapper.map(a, AdventureDTO.class));
            reservationsDTO.add(rt);
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/findHistoryByCottageOwner/getCottageOwner")
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

    @GetMapping(value = "/findHistoryByClient")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<List<ReservationDTO>> getHistoryByClient(Principal principal){
        Client client = clientService.findByEmail(principal.getName());
        List<Reservation> reservationList = reservationService.getHistoryByClient(client.getId());
        System.out.println("Rezervacijeeee: " + reservationList.size());
        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for(Reservation r: reservationList){
            ReservationDTO dto = mapper.map(r, ReservationDTO.class);
            dto.getEntity().setType(EntityKind.toString(r.getEntity().getKind()));
            reservationsDTO.add(dto);
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }
}
