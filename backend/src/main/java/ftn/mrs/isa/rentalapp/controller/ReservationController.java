package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.*;
import ftn.mrs.isa.rentalapp.model.entity.*;
import ftn.mrs.isa.rentalapp.model.entity.*;
import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.model.system_info.RankingInfo;
import ftn.mrs.isa.rentalapp.model.system_info.SystemInfo;
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
import java.time.LocalDateTime;
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

    @Autowired
    private RankingInfoService rankingInfoService;

    @Autowired
    private SystemInfoService systemInfoService;

    @Autowired
    private  UserService userService;

    @Autowired
    private EmailService emailService;


    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<ReservationDTO> getById(@PathVariable Integer id, Principal principal){
        return reservationService.get(id);
    }

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

     @GetMapping(value = "/findHistoryByInstructor")
     @PreAuthorize("hasRole('fishingInstructor')")
    public ResponseEntity<List<ReservationDTO>> getAllReservationHistoryByInstructor(Principal principal){
        FishingInstructor owner = fishingInstructorService.findByEmail(principal.getName());
        List<Reservation> reservations = reservationService.getHistoryReservationByInstructor(owner.getId());
        return getListResponseAdventure(reservations);
    }

     @GetMapping(value = "/findHistory")
     @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<ReservationDTO>> getAllReservationHistory(Principal principal){
        List<Reservation> reservations = reservationService.getHistoryReservation();
         List<ReservationDTO> reservationsDTO = new ArrayList<>();
         for(Reservation c : reservations){
             ReservationDTO rt = mapper.map(c, ReservationDTO.class);
             EntityType entity = entityService.findOne(c.getEntity().getId());
             rt.setEntity(mapper.map(entity, EntityTypeDTO.class));
             reservationsDTO.add(rt);
         }
         return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);

     }


    @GetMapping(value = "/findUpcomingByBoatOwner")
    @PreAuthorize("hasRole('boatOwner')")
    public ResponseEntity<List<ReservationDTO>> getAllUpcomingReservationByBoatOwner(Principal principal){
        BoatOwner owner = boatOwnerService.findByEmail(principal.getName());
        List<Reservation> reservations = reservationService.getFutureReservationByBoatOwner(owner.getId());
        return getListResponseBoat(reservations);
    }
    @GetMapping(value = "/findUpcomingByInstructor")
    @PreAuthorize("hasRole('fishingInstructor')")
    public ResponseEntity<List<ReservationDTO>> getAllUpcomingReservationByInstructor(Principal principal){
        FishingInstructor owner = fishingInstructorService.findByEmail(principal.getName());
        List<Reservation> reservations = reservationService.getFutureReservationByInstructor(owner.getId());
        return getListResponseAdventure(reservations);
    }

    @GetMapping(value = "/findCurrentByBoatOwner")
    @PreAuthorize("hasRole('boatOwner')")
    public ResponseEntity<List<ReservationDTO>> getAllCurrentReservationByCottageOwner(Principal principal) {
        BoatOwner owner = boatOwnerService.findByEmail(principal.getName());
        List<Reservation> reservations = reservationService.getCurrentReservationByBoatOwner(owner.getId());
        return getListResponseBoat(reservations);
    }

    @GetMapping(value = "/findCurrentByInstructor")
    @PreAuthorize("hasRole('fishingInstructor')")
    public ResponseEntity<List<ReservationDTO>> getAllCurrentReservationByInstructor(Principal principal) {
        FishingInstructor owner = fishingInstructorService.findByEmail(principal.getName());
        List<Reservation> reservations = reservationService.getCurrentReservationByInstructor(owner.getId());
        return getListResponseAdventure(reservations);
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

    private ResponseEntity<List<ReservationDTO>> getListResponseAdventure(List<Reservation> reservations) {
        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for(Reservation c : reservations){
            ReservationDTO rt = mapper.map(c, ReservationDTO.class);
            Adventure adventure = adventureService.findOne(c.getEntity().getId());
            rt.setAdventure(mapper.map(adventure, AdventureDTO.class));
            reservationsDTO.add(rt);
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }

    private ResponseEntity<List<ReservationDTO>> getReservationDTOList(List<Reservation> reservationList){
        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for(Reservation r: reservationList){
            ReservationDTO dto = mapper.map(r, ReservationDTO.class);
            dto.getEntity().setType(EntityKind.toString(r.getEntity().getKind()));
            reservationsDTO.add(dto);
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/findHistoryByClient")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<List<ReservationDTO>> getHistoryByClient(Principal principal){
        Client client = clientService.findByEmail(principal.getName());
        List<Reservation> reservationList = reservationService.getHistoryByClient(client.getId());
        return getReservationDTOList(reservationList);
    }

    @GetMapping(value = "/findUpcomingByClient")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<List<ReservationDTO>> getUpcomingByClient(Principal principal){
        Client client = clientService.findByEmail(principal.getName());
        List<Reservation> reservationList = reservationService.getUpcomingByClient(client.getId());
        return getReservationDTOList(reservationList);
    }

    @PostMapping(value = "/cancelReservation")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<String> cancelReservation(@RequestBody ReservationDTO r,Principal principal){
        Client client = clientService.findByEmail(principal.getName());
        client.setPenalties(client.getPenalties() + 1);
        clientService.save(client);
        reservationService.cancelReservation(r.getId());
        return new ResponseEntity<>("Canceled successfully.",HttpStatus.OK);
    }


    @PutMapping("/makeReservationFromQuick/{id}")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<String> makeReservationFromQuick(@PathVariable(value = "id") Integer id,Principal principal) throws MessagingException {
        try{
            return reservationService.makeReservationFromQuick(id, principal.getName());
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/reserve")
    @PreAuthorize("hasAnyRole('client','cottageOwner','boatOwner','fishingInstructor')")
    public ResponseEntity<String> reserve(@RequestBody ReserveDataDTO r,Principal principal) throws MessagingException {

        return reservationService.reserve(r, principal.getName());
    }


    @PostMapping(value = "/delete")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> delete(@RequestBody ReserveDataDTO r,Principal principal){
        EntityType entity = entityService.findOne(r.getEntityId());
        entity.setDeleted(true);
        entityService.save(entity);
        return new ResponseEntity<>("Deleted successfully.",HttpStatus.OK);
    }
}
