package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.AvailablePeriodDTO;
import ftn.mrs.isa.rentalapp.dto.FishingInstructorDTO;
import ftn.mrs.isa.rentalapp.model.entity.AvailablePeriod;
import ftn.mrs.isa.rentalapp.model.user.FishingInstructor;
import ftn.mrs.isa.rentalapp.service.AvailablePeriodService;
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
    CottageService cottageService;

    @Autowired
    private ModelMapper mapper;


    @GetMapping(value = "/getInstructor")
    public ResponseEntity<FishingInstructorDTO> getInstructor(Principal principal){
        FishingInstructor instructor = fishingInstructorService.findByEmail(principal.getName());
        if(instructor == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mapper.map(instructor,FishingInstructorDTO.class), HttpStatus.OK);
    }

    @GetMapping(value = "/getInstructorById/{id}")
    public ResponseEntity<FishingInstructorDTO> getInstructorById(@PathVariable Integer id){
        FishingInstructor instructor = fishingInstructorService.findOne(id);
        if(instructor == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mapper.map(instructor,FishingInstructorDTO.class), HttpStatus.OK);
    }


    @PostMapping(value = "/updateInstructor" )
    @PreAuthorize("hasRole('fishingInstructor')")
    public void updateInstructor(@RequestBody FishingInstructorDTO fishingInstructorDTO, Principal principal) {
        FishingInstructor fishingInstructor = fishingInstructorService.findOne(fishingInstructorDTO.getId());
        fishingInstructorDTO.setRegistrationStatus(fishingInstructor.getRegistrationStatus());
        fishingInstructorDTO.setRegistrationReason(fishingInstructor.getRegistrationReason());
        FishingInstructor updatedInstructor = mapper.map(fishingInstructorDTO,FishingInstructor.class);
        updatedInstructor.setRoles(fishingInstructor.getRoles());
        updatedInstructor.setMainPhoto(fishingInstructor.getMainPhoto());
        updatedInstructor.setPoints(fishingInstructor.getPoints());
        updatedInstructor.setEnabled(fishingInstructor.isEnabled());
        updatedInstructor.setType(fishingInstructor.getType());
        fishingInstructorService.updateInstructor(updatedInstructor);
    }



    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> delete(@PathVariable Integer id, Principal principal){
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
            if (!c.isDeleted()) {
                instructorsDTO.add(mapper.map(c, FishingInstructorDTO.class));
            }
        }
        return new ResponseEntity<>(instructorsDTO, HttpStatus.OK);
    }

}
