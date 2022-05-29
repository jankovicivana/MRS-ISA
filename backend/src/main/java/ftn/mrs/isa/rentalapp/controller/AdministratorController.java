package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.AdministratorCreateDTO;
import ftn.mrs.isa.rentalapp.dto.AdministratorDTO;
import ftn.mrs.isa.rentalapp.dto.FishingInstructorDTO;
import ftn.mrs.isa.rentalapp.model.user.Administrator;
import ftn.mrs.isa.rentalapp.model.user.FishingInstructor;
import ftn.mrs.isa.rentalapp.model.user.UserType;
import ftn.mrs.isa.rentalapp.service.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/administrator")
public class AdministratorController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AdministratorService administratorService;




    @PostMapping("/addAdministrator")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<AdministratorDTO> addAdventure(@RequestBody AdministratorCreateDTO administratorCreateDTO, Principal principal) {
        Administrator admin = mapper.map(administratorCreateDTO,Administrator.class);
        admin.setType(String.valueOf(UserType.ADMINISTRATOR));
        administratorService.save(admin);
        return new ResponseEntity<>(mapper.map(admin, AdministratorDTO.class), HttpStatus.CREATED);

    }

    @GetMapping(value = "/getAdmin")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<AdministratorDTO> getAdmin(Principal principal){
        Administrator admin = administratorService.findByEmail(principal.getName());
        if(admin == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(mapper.map(admin,AdministratorDTO.class), HttpStatus.OK);
    }

    @PostMapping(value = "/updateAdmin" )
    @PreAuthorize("hasRole('admin')")
    public void updateAdmin(@RequestBody AdministratorDTO administratorDTO, Principal principal) {
        Administrator oldAdmin = administratorService.findOne(administratorDTO.getId());
        Administrator a = mapper.map(administratorDTO,Administrator.class);
        a.setRoles(oldAdmin.getRoles());
        administratorService.save(a);
    }



}
