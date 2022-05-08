package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.AdventureCreateDTO;
import ftn.mrs.isa.rentalapp.dto.AdventureDTO;
import ftn.mrs.isa.rentalapp.dto.QuickReservationDTO;
import ftn.mrs.isa.rentalapp.model.entity.*;
import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.service.AdventureService;
import ftn.mrs.isa.rentalapp.service.CottageService;
import ftn.mrs.isa.rentalapp.service.QuickReservationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

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


    @PostMapping("/addQuickReservation")
    public ResponseEntity<QuickReservationDTO> addQuickReservation(@RequestBody QuickReservationDTO quickReservationDTO) throws Exception {
        EntityType entity = adventureService.findOne(quickReservationDTO.getEntId());
        if (entity == null){
            entity = cottageService.findOne(quickReservationDTO.getEntId());
        }
        QuickReservation quickReservation = mapper.map(quickReservationDTO, QuickReservation.class);
        quickReservation.setIsReserved(false);
        quickReservation.setEntity(entity);
        quickReservation.setIsReserved(false);
        quickReservation.setReservation(null);
        System.out.print(quickReservation.getExpirationDateTime());
        quickReservationService.save(quickReservation);

        return new ResponseEntity<>(mapper.map(quickReservation,QuickReservationDTO.class), HttpStatus.CREATED);

    }

}
