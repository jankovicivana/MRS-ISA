package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.AdministratorCreateDTO;
import ftn.mrs.isa.rentalapp.dto.AdministratorDTO;
import ftn.mrs.isa.rentalapp.model.user.Administrator;
import ftn.mrs.isa.rentalapp.model.user.UserType;
import ftn.mrs.isa.rentalapp.service.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
