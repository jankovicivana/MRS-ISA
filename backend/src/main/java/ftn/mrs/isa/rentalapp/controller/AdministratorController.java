package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.AdministratorCreateDTO;
import ftn.mrs.isa.rentalapp.dto.AdministratorDTO;
import ftn.mrs.isa.rentalapp.dto.FishingInstructorDTO;
import ftn.mrs.isa.rentalapp.model.user.Administrator;
import ftn.mrs.isa.rentalapp.model.user.FishingInstructor;
import ftn.mrs.isa.rentalapp.model.user.User;
import ftn.mrs.isa.rentalapp.model.user.UserType;
import ftn.mrs.isa.rentalapp.service.AdministratorService;
import ftn.mrs.isa.rentalapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.security.Principal;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/administrator")
public class AdministratorController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private UserService userService;




    @PostMapping("/addAdministrator")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<AdministratorDTO> addAdventure(@RequestBody AdministratorCreateDTO administratorCreateDTO, Principal principal) {
        UserDetails existUser = this.userService.loadUserByUsername(administratorCreateDTO.getEmail());
        if (existUser != null) {
            return new ResponseEntity<>(new AdministratorDTO(),HttpStatus.BAD_REQUEST);
        }
        Administrator admin = mapper.map(administratorCreateDTO,Administrator.class);
        admin.setType(String.valueOf(UserType.ADMINISTRATOR));
        admin.setPasswordChanged(false);
        admin.setPoints(0);
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
        AdministratorDTO dto = mapper.map(admin,AdministratorDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping(value = "/updateAdmin" )
    @PreAuthorize("hasRole('admin')")
    public void updateAdmin(@RequestBody AdministratorDTO administratorDTO, Principal principal) {
        Administrator oldAdmin = administratorService.findOne(administratorDTO.getId());
        Administrator a = mapper.map(administratorDTO,Administrator.class);
        a.setRoles(oldAdmin.getRoles());
        a.setPoints(0);
        administratorService.save(a);
    }

    @PostMapping(value = "/changePassword")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> changePassword(@RequestBody String newPassword, Principal principal) throws InterruptedException, MessagingException {
        Administrator u = administratorService.findByEmail(principal.getName());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        u.setPassword(passwordEncoder.encode(newPassword.substring(0,newPassword.length()-1)));
        u.setPasswordChanged(true);
        administratorService.save(u);
        return new ResponseEntity<>("Accepting is successful.",HttpStatus.OK);
    }



}
