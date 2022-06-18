package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.AccountDeleteRequestDTO;
import ftn.mrs.isa.rentalapp.dto.AdvertiserDTO;
import ftn.mrs.isa.rentalapp.dto.PasswordDTO;
import ftn.mrs.isa.rentalapp.dto.RegistrationResponse;
import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.model.user.AccountDeleteRequest;
import ftn.mrs.isa.rentalapp.model.user.Advertiser;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.model.user.User;
import ftn.mrs.isa.rentalapp.service.EmailService;
import ftn.mrs.isa.rentalapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @PostMapping(value = "/acceptDeletion")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> acceptDeletion(@RequestBody AccountDeleteRequestDTO accountDeleteRequestDTO, Principal principal) throws InterruptedException, MessagingException {
        if(userService.setDeletionStatus(accountDeleteRequestDTO,RequestStatus.ACCEPTED)){
        return new ResponseEntity<>("Accepting is successful.",HttpStatus.OK);
        }
        return new ResponseEntity<>("Accepting is denied.",HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/rejectDeletion")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> rejectDeletion(@RequestBody AccountDeleteRequestDTO accountDeleteRequestDTO, Principal principal) throws InterruptedException, MessagingException {
        if(userService.setDeletionStatus(accountDeleteRequestDTO,RequestStatus.REJECTED)){
            return new ResponseEntity<>("Rejecting is successful.",HttpStatus.OK);
        }
        return new ResponseEntity<>("Rejecting is denied.",HttpStatus.BAD_REQUEST);
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

    @GetMapping("/getDeleteRequestsOnHold")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<AccountDeleteRequestDTO>> getDeleteRequestsOnHold(Principal principal) {
        List<AccountDeleteRequest> requests = userService.getDeleteRequestOnHold();
        List<AccountDeleteRequestDTO> requestDTOS = new ArrayList<>();
        for (AccountDeleteRequest d: requests){
            AccountDeleteRequestDTO a = mapper.map(d,AccountDeleteRequestDTO.class);
            requestDTOS.add(a);
        }
        return new ResponseEntity<>(requestDTOS, HttpStatus.CREATED);

    }

    @PostMapping(value = "/changePassword")
    @PreAuthorize("hasAnyRole('admin','cottageOwner','boatOwner','fishingInstructor','client')")
    public ResponseEntity<String> changePass(@RequestBody PasswordDTO passwordDTO, Principal principal) {
        User u = userService.findUserByEmail(principal.getName());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(passwordEncoder.matches(passwordDTO.currentPassword,u.getPassword())){
            u.setPassword(passwordEncoder.encode(passwordDTO.newPassword));
            userService.saveUser(u);
            return new ResponseEntity<>("Changing is successful.",HttpStatus.OK);
        }
        return new ResponseEntity<>("Changing password is unsuccessful.",HttpStatus.NOT_FOUND);
    }

    @PostMapping("/deleteAccount")
    @PreAuthorize("hasAnyRole('admin','cottageOwner','boatOwner','fishingInstructor','client')")
    public ResponseEntity<String> deleteAccount(@RequestBody AccountDeleteRequestDTO accountDeleteRequestDTO,Principal principal){
        User u = userService.findUserByEmail(principal.getName());
        AccountDeleteRequest accountDeleteRequest = new AccountDeleteRequest();
        accountDeleteRequest.setUserId(u);
        accountDeleteRequest.setRequestReason(accountDeleteRequestDTO.getRequestReason());
        accountDeleteRequest.setStatus(RequestStatus.ON_HOLD);
        userService.saveDeleteRequest(accountDeleteRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
