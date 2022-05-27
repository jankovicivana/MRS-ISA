package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.dto.UserRequest;
import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.model.entity.Boat;
import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.model.user.BoatOwner;
import ftn.mrs.isa.rentalapp.model.user.CottageOwner;
import ftn.mrs.isa.rentalapp.model.user.Role;
import ftn.mrs.isa.rentalapp.repository.CottageOwnerRepository;
import ftn.mrs.isa.rentalapp.repository.CottageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CottageOwnerService {

    @Autowired
    CottageOwnerRepository cottageOwnerRepository;

    @Autowired
    private RoleService roleService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    CottageRepository cottageRepository;

    @Autowired
    ReservationService reservationService;

    public CottageOwner findOne(Integer id){return cottageOwnerRepository.findById(id).orElse(null);}

    public CottageOwner findByEmail(String email){return  cottageOwnerRepository.findByEmail(email);}

    public List<CottageOwner> findAll() {
        return cottageOwnerRepository.findAll();
    }

    public boolean canDeleteCottageOwner(CottageOwner owner) {
        List<Reservation> l = reservationService.getFutureReservationByCottageOwner(owner.getId());
        return l.size() == 0;
    }

    public void deleteCottageOwner(CottageOwner owner) {
        owner.setDeleted(true);
        cottageOwnerRepository.save(owner);
        List<Reservation> reservations = reservationService.getReservationByBoatOwner(owner.getId());
        for(Cottage a : owner.getCottages()) {
            a.setDeleted(true);
        }
        cottageRepository.saveAll(owner.getCottages());
        for(Reservation r : reservations){
            r.setDeleted(true);
        }
        reservationService.saveAll(reservations);
    }



    public void updateCottageOwner(CottageOwner cottageOwner){
        cottageOwnerRepository.save(cottageOwner);
    }

    public CottageOwner save(UserRequest userRequest) {
        CottageOwner c = new CottageOwner();
        c.setEmail(userRequest.getEmail());
        c.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        c.setName(userRequest.getName());
        c.setSurname(userRequest.getSurname());
        c.setAddress(new Address(userRequest.getStreet(), userRequest.getCity(), userRequest.getPostalCode(), userRequest.getCountry()));
        c.setEnabled(true);
        c.setPhoneNumber(userRequest.getPhoneNumber());
        c.setPoints(0);
        c.setRegistrationStatus(RequestStatus.ON_HOLD);
        c.setRegistrationReason(userRequest.getReason());
        List<Role> roles = roleService.findByName(userRequest.getRole());
        c.setRoles(roles);

        return this.cottageOwnerRepository.save(c);
    }
}
