package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.AvailablePeriodDTO;
import ftn.mrs.isa.rentalapp.model.entity.AvailablePeriod;
import ftn.mrs.isa.rentalapp.model.user.FishingInstructor;
import ftn.mrs.isa.rentalapp.service.AvailablePeriodService;
import ftn.mrs.isa.rentalapp.service.BoatService;
import ftn.mrs.isa.rentalapp.service.CottageService;
import ftn.mrs.isa.rentalapp.service.FishingInstructorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/availablePeriod")
public class AvailablePeriodController {

    @Autowired
    FishingInstructorService fishingInstructorService;

    @Autowired
    AvailablePeriodService availablePeriodService;

    @Autowired
    CottageService cottageService;

    @Autowired
    BoatService boatService;


    @PostMapping(value = "/addCottage" )
    @PreAuthorize("hasRole('cottageOwner')")
    public void addAvailablePeriodCottage(@RequestBody AvailablePeriodDTO availablePeriod, Principal principal) {
        AvailablePeriod a = new AvailablePeriod();
        a.setStartDateTime(availablePeriod.getStartDateTime());
        a.setEndDateTime(availablePeriod.getEndDateTime());
        a.setEntity(cottageService.findOne(availablePeriod.getEntity()));
        availablePeriodService.add(a);
    }

    @PostMapping(value = "/addBoat" )
    @PreAuthorize("hasRole('boatOwner')")
    public void addAvailablePeriodBoat(@RequestBody AvailablePeriodDTO availablePeriod, Principal principal) {
        AvailablePeriod a = new AvailablePeriod();
        a.setStartDateTime(availablePeriod.getStartDateTime());
        a.setEndDateTime(availablePeriod.getEndDateTime());
        a.setEntity(boatService.findOne(availablePeriod.getEntity()));
        availablePeriodService.add(a);
    }

    @PostMapping(value = "/addForFishingInstructor" )
    @PreAuthorize("hasRole('fishingInstructor')")
    public void addAvailablePeriodFishing(@RequestBody AvailablePeriodDTO availablePeriod, Principal principal) {
        AvailablePeriod a = new AvailablePeriod();
        a.setStartDateTime(availablePeriod.getStartDateTime());
        a.setEndDateTime(availablePeriod.getEndDateTime());
        a.setEntity(null);
        FishingInstructor fishingInstructor = fishingInstructorService.findByEmail(principal.getName());
        a.setFishingInstructor(fishingInstructor);
        availablePeriodService.add(a);
    }

    @GetMapping(value = "/getAvailablePeriod/getInstructor")
    @PreAuthorize("hasRole('fishingInstructor')")
    public ResponseEntity<List<AvailablePeriodDTO>> getAvailablePeriodOfInstructor(Principal principal){
        FishingInstructor instructor = fishingInstructorService.findByEmail(principal.getName());
        if(instructor == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<AvailablePeriod>  periods = availablePeriodService.getAvailablePeriodFishingInstructor(instructor.getId());
        List<AvailablePeriodDTO> availablePeriods = new ArrayList<>();
        for (AvailablePeriod a : periods){
            AvailablePeriodDTO availablePeriodDTO = new AvailablePeriodDTO();
            availablePeriodDTO.setStartDateTime(a.getStartDateTime());
            availablePeriodDTO.setEndDateTime(a.getEndDateTime());
            availablePeriodDTO.setFishingInstructor(instructor.getId());
            availablePeriodDTO.setEntity(null);
            availablePeriods.add(availablePeriodDTO);
        }
        return new ResponseEntity<>(availablePeriods, HttpStatus.OK);
    }

    @GetMapping(value = "/getAvailablePeriod/{id}")
    public ResponseEntity<List<AvailablePeriodDTO>> getAvailablePeriodOfInstructor(@PathVariable Integer id){
        List<AvailablePeriod> periods = availablePeriodService.getAvailablePeriodEntity(id);
        List<AvailablePeriodDTO> availablePeriods = new ArrayList<>();
        for (AvailablePeriod a : periods){
            AvailablePeriodDTO availablePeriodDTO = new AvailablePeriodDTO();
            availablePeriodDTO.setStartDateTime(a.getStartDateTime());
            availablePeriodDTO.setEndDateTime(a.getEndDateTime());
            availablePeriodDTO.setFishingInstructor(null);
            availablePeriodDTO.setEntity(id);
            availablePeriods.add(availablePeriodDTO);
        }
        return new ResponseEntity<>(availablePeriods, HttpStatus.OK);
    }


}
