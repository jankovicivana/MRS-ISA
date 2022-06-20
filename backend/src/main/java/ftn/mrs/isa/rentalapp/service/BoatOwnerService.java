package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.dto.BoatOwnerDTO;
import ftn.mrs.isa.rentalapp.dto.UserRequest;
import ftn.mrs.isa.rentalapp.model.entity.Boat;
import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.model.user.BoatOwner;
import ftn.mrs.isa.rentalapp.model.user.Role;
import ftn.mrs.isa.rentalapp.repository.BoatOwnerRepository;
import ftn.mrs.isa.rentalapp.repository.BoatRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoatOwnerService {

    @Autowired
    private BoatOwnerRepository boatOwnerRepository;

    @Autowired
    private BoatRepository boatRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RoleService roleService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public BoatOwner findByEmail(String string) {return boatOwnerRepository.findByEmail(string);}

    public List<BoatOwner> findAll() {
        return boatOwnerRepository.findAll();
    }

    public ResponseEntity<String> updateBoatOwner(BoatOwnerDTO boatOwnerDTO){
        BoatOwner boatOwner = findOne(boatOwnerDTO.getId());
        if(boatOwner == null){
            return new ResponseEntity<>("Owner not found", HttpStatus.BAD_REQUEST);
        }
        if(boatOwnerDTO.getName().length() == 0 || boatOwnerDTO.getSurname().length() == 0 || boatOwnerDTO.getAddress().getCity().length() == 0
                || boatOwnerDTO.getAddress().getCountry().length()==0 || boatOwnerDTO.getAddress().getStreet().length() == 0){
            return new ResponseEntity<>("Values must not be empty.",HttpStatus.BAD_REQUEST);
        }

        try {
            Integer.parseInt(boatOwnerDTO.getPhoneNumber());
            Integer.parseInt(boatOwnerDTO.getAddress().getPostalCode());
        }catch (NumberFormatException nfe){
            return new ResponseEntity<>("Phone number and postal code must be numbers.",HttpStatus.BAD_REQUEST);
        }

        boatOwnerDTO.setRegistrationStatus(boatOwner.getRegistrationStatus());
        BoatOwner updated = mapper.map(boatOwnerDTO,BoatOwner.class);
        updated.setRoles(boatOwner.getRoles());
        updated.setMainPhoto(boatOwner.getMainPhoto());
        updated.setPoints(boatOwner.getPoints());
        updated.setEnabled(boatOwner.isEnabled());
        updated.setRegistrationReason(boatOwner.getRegistrationReason());
        updated.setType(boatOwner.getType());
        boatOwnerRepository.save(updated);
        return new ResponseEntity<>("Successfully edited.",HttpStatus.OK);
    }



    public BoatOwner save(UserRequest userRequest) {
        BoatOwner c = new BoatOwner();
        UserService.setUserInfo(userRequest, c, passwordEncoder);
        c.setPhoneNumber(userRequest.getPhoneNumber());
        c.setPoints(0);
        c.setRegistrationStatus(RequestStatus.ON_HOLD);
        c.setRegistrationReason(userRequest.getReason());
        List<Role> roles = roleService.findByName(userRequest.getRole());
        c.setRoles(roles);

        return this.boatOwnerRepository.save(c);
    }

    public boolean canDeleteBoatOwner(BoatOwner owner) {
        List<Reservation> l = reservationService.getFutureReservationByBoatOwner(owner.getId());
        return l.size() == 0;
    }

    public void deleteBoatOwner(BoatOwner owner) {
        owner.setDeleted(true);
        boatOwnerRepository.save(owner);
        List<Reservation> reservations = reservationService.getReservationByBoatOwner(owner.getId());
        for(Boat a : owner.getBoats()) {
            a.setDeleted(true);
        }
        boatRepository.saveAll(owner.getBoats());
        for(Reservation r : reservations){
            r.setDeleted(true);
        }
        reservationService.saveAll(reservations);
    }

    public List<BoatOwnerDTO> getAllBoatOwners(){
        List<BoatOwner> owners = findAll();
        List<BoatOwnerDTO> ownersDTO = new ArrayList<>();
        for(BoatOwner c : owners){
            if (!c.isDeleted()) {
                ownersDTO.add(mapper.map(c, BoatOwnerDTO.class));
            }
        }
        return ownersDTO;
    }


    public BoatOwner findOne(Integer id) {
        return boatOwnerRepository.findById(id).orElse(null);
    }
}
