package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.BoatOwnerDTO;
import ftn.mrs.isa.rentalapp.dto.CottageOwnerDTO;
import ftn.mrs.isa.rentalapp.dto.FishingInstructorDTO;
import ftn.mrs.isa.rentalapp.model.user.BoatOwner;
import ftn.mrs.isa.rentalapp.model.user.CottageOwner;
import ftn.mrs.isa.rentalapp.model.user.FishingInstructor;
import ftn.mrs.isa.rentalapp.service.*;
import ftn.mrs.isa.rentalapp.service.BoatOwnerService;
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
@RequestMapping("api/boatOwner")
public class BoatOwnerController {

    @Autowired
    private BoatOwnerService boatOwnerService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(value = "/getBoatOwner")
    @PreAuthorize("hasRole('boatOwner')")
    public ResponseEntity<BoatOwnerDTO> getBoatOwner(Principal principal){
        BoatOwner boatOwner = boatOwnerService.findByEmail(principal.getName());
        if(boatOwner == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mapper.map(boatOwner,BoatOwnerDTO.class), HttpStatus.OK);
    }



    @PostMapping(value = "/updateBoatOwner" )
    @PreAuthorize("hasRole('boatOwner')")
    public ResponseEntity<String> updateBoatOwner(@RequestBody BoatOwnerDTO boatOwnerDTO) {
        BoatOwner boatOwner = boatOwnerService.findOne(boatOwnerDTO.getId());
        if(boatOwner == null){
            return new ResponseEntity<>("Owner not found",HttpStatus.BAD_REQUEST);
        }
        if(boatOwnerDTO.getName().length() == 0 || boatOwnerDTO.getSurname().length() == 0 || boatOwnerDTO.getAddress().getCity().length() == 0
                || boatOwnerDTO.getAddress().getCountry().length()==0 || boatOwnerDTO.getAddress().getStreet().length() == 0){
            return new ResponseEntity<>("Values must not be empty.",HttpStatus.BAD_REQUEST);
        }

        try {
            Integer.parseInt(boatOwnerDTO.getPhoneNumber());
            Integer.parseInt(boatOwnerDTO.getAddress().getPostalCode());
        }catch (NumberFormatException nfe){
            return new ResponseEntity<>("Phone number and postal code must be numbers.",HttpStatus.BAD_REQUEST);
        }

        boatOwnerDTO.setRegistrationStatus(boatOwner.getRegistrationStatus());
        BoatOwner updated = mapper.map(boatOwnerDTO,BoatOwner.class);
        updated.setRoles(boatOwner.getRoles());
        updated.setMainPhoto(boatOwner.getMainPhoto());
        updated.setPoints(boatOwner.getPoints());
        updated.setEnabled(boatOwner.isEnabled());
        updated.setRegistrationReason(boatOwner.getRegistrationReason());
        updated.setType(boatOwner.getType());
        boatOwnerService.updateBoatOwner(updated);
        return new ResponseEntity<>("Successfully edited.",HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> delete(@PathVariable Integer id, Principal principal){
        BoatOwner client = boatOwnerService.findOne(id);
        if(client == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!boatOwnerService.canDeleteBoatOwner(client)){
            return new ResponseEntity<>("Instructor has reservations.Deletion is not possible.",HttpStatus.OK);
        }
        boatOwnerService.deleteBoatOwner(client);
        return new ResponseEntity<>("Deletion is successful.",HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<BoatOwnerDTO>> getAllBoatOwners(Principal principal){
        List<BoatOwner> owners = boatOwnerService.findAll();
        List<BoatOwnerDTO> ownersDTO = new ArrayList<>();
        for(BoatOwner c : owners){
            if (!c.isDeleted()) {
                ownersDTO.add(mapper.map(c, BoatOwnerDTO.class));
            }
        }
        return new ResponseEntity<>(ownersDTO, HttpStatus.OK);
    }

}
