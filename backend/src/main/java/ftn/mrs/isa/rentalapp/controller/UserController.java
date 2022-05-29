package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.AdvertiserDTO;
import ftn.mrs.isa.rentalapp.dto.RegistrationResponse;
import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import ftn.mrs.isa.rentalapp.model.user.Advertiser;
import ftn.mrs.isa.rentalapp.service.EmailService;
import ftn.mrs.isa.rentalapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/isEmailAvailable")
    public ResponseEntity<Boolean> isEmailAvailable(@RequestBody String email) {
        System.out.print("dosol");
        return new ResponseEntity<>(false, HttpStatus.CREATED);

        //administratorService.save(admin);
        //return new ResponseEntity<>(mapper.map(admin, AdministratorDTO.class), HttpStatus.CREATED);

    }


    @GetMapping("/getUsersOnHold")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<AdvertiserDTO>> getUsersOnHold(Principal principal) {
        List<Advertiser> advertisers = userService.getAdvertisersOnHold();
        List<AdvertiserDTO> advertiserDTOS = new ArrayList<>();
        for (Advertiser d: advertisers){
            AdvertiserDTO a = mapper.map(d,AdvertiserDTO.class);
            a.setRegistrationReason(d.getRegistrationReason());
            System.out.print(a.getRegistrationReason());
            advertiserDTOS.add(a);
        }
        return new ResponseEntity<>(advertiserDTOS, HttpStatus.CREATED);

    }

    @PostMapping(value = "/rejectRegistration")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> rejectRegistration(@RequestBody RegistrationResponse registrationResponse, Principal principal) throws InterruptedException, MessagingException {
        Advertiser a = userService.findAdvertiserById(registrationResponse.getId());
        a.setRegistrationStatus(RequestStatus.REJECTED);
        userService.saveAdvertiser(a);
        emailService.sendRegistrationRejected(a,registrationResponse.getReason());
        return new ResponseEntity<>("Accepting is successful.",HttpStatus.OK);
    }

    @GetMapping(value = "/acceptRegistration/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> acceptRegistration(@PathVariable Integer id, Principal principal) throws InterruptedException, MessagingException {
        Advertiser a = userService.findAdvertiserById(id);
        a.setRegistrationStatus(RequestStatus.ACCEPTED);
        userService.saveAdvertiser(a);
        emailService.sendRegistrationAccepted(a);
        return new ResponseEntity<>("Accepting is successful.",HttpStatus.OK);
    }


}
