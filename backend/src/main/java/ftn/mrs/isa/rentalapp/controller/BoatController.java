package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.BoatCreateDTO;
import ftn.mrs.isa.rentalapp.dto.BoatDTO;
import ftn.mrs.isa.rentalapp.dto.CottageDTO;
import ftn.mrs.isa.rentalapp.dto.EntitySearchDTO;
import ftn.mrs.isa.rentalapp.model.entity.*;
import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.model.user.BoatOwner;
import ftn.mrs.isa.rentalapp.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/boats")
public class BoatController {

    @Autowired
    private BoatService boatService;


    @Autowired
    private ModelMapper mapper;


    @GetMapping(value = "/all")
    public ResponseEntity<List<BoatDTO>> getAllBoats(){
        List<BoatDTO> boatsDTO = boatService.getAllBoats();
        return new ResponseEntity<>(boatsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/allByOwner")
    @PreAuthorize("hasRole('boatOwner')")
    public ResponseEntity<List<BoatDTO>> getAllBoatsByOwner(Principal principal){
        List<BoatDTO> boatsDTO = boatService.getAllBoatsByOwner(principal.getName());
        return new ResponseEntity<>(boatsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BoatDTO> getBoat(@PathVariable Integer id){
        Boat boat = boatService.findOne(id);
        if(boat == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BoatDTO b = mapper.map(boat,BoatDTO.class);
        b.setType(BoatType.toString(boat.getType()));
        return new ResponseEntity<>(b,HttpStatus.OK);
    }

    @PostMapping("/addBoat")
    @PreAuthorize("hasRole('boatOwner')")
    public ResponseEntity<BoatDTO> addBoat(@RequestBody BoatCreateDTO boatCreateDTO,Principal principal) throws IOException {
        Boat boat = boatService.createBoat(boatCreateDTO,principal.getName());
        return new ResponseEntity<>(mapper.map(boat,BoatDTO.class),HttpStatus.CREATED);
    }

    @PutMapping("/updateBoat")
    @PreAuthorize("hasRole('boatOwner')")
    public ResponseEntity<BoatDTO> updateBoat(@RequestBody BoatDTO boatDTO,Principal principal){
        Boat boat = boatService.updateBoat(boatDTO);
        if(boat == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(mapper.map(boat,BoatDTO.class),HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteBoat/{id}")
    @PreAuthorize("hasAnyRole('admin','boatOwner')")
    public ResponseEntity<String> deleteBoat(@PathVariable Integer id,Principal principal){
        return boatService.deleteBoat(id);
    }

    @GetMapping(value = "/getAverageGrade")
    @PreAuthorize("hasRole('boatOwner')")
    public ResponseEntity<Double> getBoatGrade(Principal principal){
        double averageGrade = boatService.getBoatsAverageGrade(principal.getName());
        return new ResponseEntity<>(averageGrade, HttpStatus.OK);
    }

    @PostMapping(value = "/getAvailable")
    @PreAuthorize("hasRole('boatOwner')")
    public ResponseEntity<List<BoatDTO>> getAvailable(@RequestBody EntitySearchDTO params, Principal principal){
        List<BoatDTO> entitiesDTO = boatService.getAvailableBoats(params,principal.getName());
        return new ResponseEntity<>(entitiesDTO, HttpStatus.OK);
    }

}
