package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.AdventureDTO;
import ftn.mrs.isa.rentalapp.dto.ReservationDTO;
import ftn.mrs.isa.rentalapp.model.entity.Adventure;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.service.AdventureService;
import ftn.mrs.isa.rentalapp.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/findHistoryByUser/{id}")
    public ResponseEntity<List<ReservationDTO>> getAllReservationHistoryByUser(@PathVariable Integer id){
        List<Reservation> reservations = reservationService.findAllHistoryByUser(id);
        System.out.print("------>"+reservations.size());
        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for(Reservation c : reservations){
            ReservationDTO rt = mapper.map(c, ReservationDTO.class);
            Adventure a = adventureService.findOne(c.getEntity().getId());
            rt.setAdventure(mapper.map(a, AdventureDTO.class));
            reservationsDTO.add(rt);
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }
}
