package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.dto.UserRequest;
import ftn.mrs.isa.rentalapp.model.entity.Boat;
import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.model.user.BoatOwner;
import ftn.mrs.isa.rentalapp.model.user.Role;
import ftn.mrs.isa.rentalapp.repository.BoatOwnerRepository;
import ftn.mrs.isa.rentalapp.repository.BoatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoatOwnerService {

    @Autowired
    BoatOwnerRepository boatOwnerRepository;

    @Autowired
    BoatRepository boatRepository;

    @Autowired
    ReservationService reservationService;

    @Autowired
    private RoleService roleService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public BoatOwner findByEmail(String string) {return boatOwnerRepository.findByEmail(string);}

    public List<BoatOwner> findAll() {
        return boatOwnerRepository.findAll();
    }

    public void updateBoatOwner(BoatOwner boatOwner){
        boatOwnerRepository.save(boatOwner);
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


    public BoatOwner findOne(Integer id) {
        return boatOwnerRepository.findById(id).orElse(null);
    }
}
