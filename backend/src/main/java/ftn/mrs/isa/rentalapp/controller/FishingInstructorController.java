package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.AvailablePeriodDTO;
import ftn.mrs.isa.rentalapp.dto.ClientDTO;
import ftn.mrs.isa.rentalapp.dto.FishingInstructorDTO;
import ftn.mrs.isa.rentalapp.model.entity.Adventure;
import ftn.mrs.isa.rentalapp.model.entity.AvailablePeriod;
import ftn.mrs.isa.rentalapp.model.entity.Image;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.model.user.FishingInstructor;
import ftn.mrs.isa.rentalapp.service.AdditionalServiceService;
import ftn.mrs.isa.rentalapp.service.AvailablePeriodService;
import ftn.mrs.isa.rentalapp.service.FishingInstructorService;
import ftn.mrs.isa.rentalapp.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/fishingInstructor")
public class FishingInstructorController {

    @Autowired
    FishingInstructorService fishingInstructorService;

    @Autowired
    AvailablePeriodService availablePeriodService;

    @Autowired
    private ModelMapper mapper;


    @GetMapping(value = "/{id}")
    public ResponseEntity<FishingInstructorDTO> getInstructor(@PathVariable Integer id){
        System.out.print("uslo");
        FishingInstructor instructor = fishingInstructorService.findOne(id);
        if(instructor == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.print("naslo");
        System.out.print(instructor);
        return new ResponseEntity<>(mapper.map(instructor,FishingInstructorDTO.class), HttpStatus.OK);
    }



    @PostMapping(value = "/updateInstructor" )
    public void updateInstructor(@RequestBody FishingInstructorDTO fishingInstructorDTO) {
        FishingInstructor fishingInstructor = fishingInstructorService.findOne(fishingInstructorDTO.getId());
        fishingInstructorDTO.setId(3); //vidi ovoooooo
        fishingInstructorDTO.setRegistrationStatus(fishingInstructor.getRegistrationStatus());
        fishingInstructorService.updateInstructor(mapper.map(fishingInstructorDTO,FishingInstructor.class));
    }

    @PostMapping(value = "/addAvailablePeriod" )
    public void updateInstructor(@RequestBody AvailablePeriodDTO availablePeriod) {
        availablePeriodService.add(mapper.map(availablePeriod,AvailablePeriod.class));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        FishingInstructor client = fishingInstructorService.findOne(id);
        if(client == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!fishingInstructorService.canDeleteInstructor(client)){
            return new ResponseEntity<>("Instructor has reservations.Deletion is not possible.",HttpStatus.OK);
        }
        fishingInstructorService.deleteInstructor(client);
        return new ResponseEntity<>("Deletion is successful.",HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<FishingInstructorDTO>> getAllInstructors(){
        List<FishingInstructor> instructors = fishingInstructorService.findAll();
        List<FishingInstructorDTO> instructorsDTO = new ArrayList<>();
        for(FishingInstructor c : instructors){
            instructorsDTO.add(mapper.map(c,FishingInstructorDTO.class));
        }
        return new ResponseEntity<>(instructorsDTO, HttpStatus.OK);
    }

}
