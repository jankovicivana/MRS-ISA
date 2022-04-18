package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.AdventureDTO;
import ftn.mrs.isa.rentalapp.dto.AdventureCreateDTO;
import ftn.mrs.isa.rentalapp.dto.QuickReservationDTO;
import ftn.mrs.isa.rentalapp.model.entity.*;
import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import ftn.mrs.isa.rentalapp.service.AdventureService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/adventures")
public class AdventureController {

    @Autowired
    private AdventureService adventureService;

   @Autowired
   private ModelMapper modelMapper;

    @GetMapping(value = "/{id}")
    public ResponseEntity<AdventureDTO> getCottage(@PathVariable Integer id){
        Adventure adventure = adventureService.findOne(id);
        if(adventure == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AdventureDTO dto = modelMapper.map(adventure,AdventureDTO.class);
        dto.setBiography(adventure.getFishingInstructor().getBiography());
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    }

