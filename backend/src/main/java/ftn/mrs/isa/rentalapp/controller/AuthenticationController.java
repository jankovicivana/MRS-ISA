package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.JwtAuthenticationRequest;
import ftn.mrs.isa.rentalapp.dto.UserRequest;
import ftn.mrs.isa.rentalapp.dto.UserTokenState;
import ftn.mrs.isa.rentalapp.exception.ResourceConflictException;
import ftn.mrs.isa.rentalapp.model.user.*;
import ftn.mrs.isa.rentalapp.service.*;
import ftn.mrs.isa.rentalapp.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;


//Kontroler zaduzen za autentifikaciju korisnika
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private CottageOwnerService cottageOwnerService;

    @Autowired
    private BoatOwnerService boatOwnerService;

    @Autowired
    private FishingInstructorService fishingInstructorService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmailService emailService;

    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {

        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
        // AuthenticationException

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println("Lozinkaaaaaaa: " + passwordEncoder.encode("pass"));
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch(InternalAuthenticationServiceException e){
            return ResponseEntity.badRequest().body(null);
        } catch (DisabledException e){
            return  ResponseEntity.ok(null);
        }

        // InternalAuthenticationServiceException - ako ne postoji
        //DisabledException - ako je disabled
        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        // kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        User user = (User) authentication.getPrincipal();

        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();
        Role role = user.getRoles().get(0);

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, role.getName()));
    }

    @PostMapping("/register")
    public ResponseEntity<User> addUser(@RequestBody UserRequest userRequest) throws MessagingException {

        UserDetails existUser = this.userService.loadUserByUsername(userRequest.getEmail());

        if (existUser != null) {
            throw new ResourceConflictException(userRequest.getId(), "Username already exists"); // vrati bad request
        }
        // treba dodati i za ostale u zavisnosti od uloge
        if(userRequest.getRole().equals("ROLE_client")){
            Client client = this.clientService.save(userRequest);
            System.out.println("Maaaaaaaaailll: " + client.getEmail());
            emailService.sendRegistrationActivation(client);
            return new ResponseEntity<>(client, HttpStatus.CREATED);
        }else if(userRequest.getRole().equals("ROLE_cottageOwner")){
            CottageOwner cottageOwner = this.cottageOwnerService.save(userRequest);
            return new ResponseEntity<>(cottageOwner, HttpStatus.CREATED);
        }else if(userRequest.getRole().equals("ROLE_boatOwner")){
            BoatOwner boatOwner = this.boatOwnerService.save(userRequest);
            return new ResponseEntity<>(boatOwner, HttpStatus.CREATED);
        }else if(userRequest.getRole().equals("ROLE_fishingInstructor")){
            FishingInstructor fishingInstructor = this.fishingInstructorService.save(userRequest);
            return new ResponseEntity<>(fishingInstructor, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


}