package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.dto.CottageOwnerDTO;
import ftn.mrs.isa.rentalapp.dto.UserRequest;
import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.model.user.CottageOwner;
import ftn.mrs.isa.rentalapp.model.user.Role;
import ftn.mrs.isa.rentalapp.repository.CottageOwnerRepository;
import ftn.mrs.isa.rentalapp.repository.CottageRepository;
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
public class CottageOwnerService {

    @Autowired
    CottageOwnerRepository cottageOwnerRepository;

    @Autowired
    private RoleService roleService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    CottageRepository cottageRepository;

    @Autowired
    private ModelMapper mapper;

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

    public List<CottageOwnerDTO> getAllCottageOwners(){
        List<CottageOwner> owners = findAll();
        List<CottageOwnerDTO> ownersDTO = new ArrayList<>();
        for(CottageOwner c : owners){
            if (!c.isDeleted()) {
                ownersDTO.add(mapper.map(c, CottageOwnerDTO.class));
            }
        }
        return ownersDTO;
    }


    public ResponseEntity<String> updateCottageOwner(CottageOwnerDTO cottageOwnerDTO, String email){
        CottageOwner cottageOwner = findByEmail(email);
        if(cottageOwner == null){
            return new ResponseEntity<>("Owner not found.", HttpStatus.BAD_REQUEST);
        }
        if(cottageOwnerDTO.getName().length() == 0 || cottageOwnerDTO.getSurname().length() == 0 || cottageOwnerDTO.getAddress().getCity().length() == 0
                || cottageOwnerDTO.getAddress().getCountry().length()==0 || cottageOwnerDTO.getAddress().getStreet().length() == 0){
            return new ResponseEntity<>("Values must not be empty.",HttpStatus.BAD_REQUEST);
        }
        try {
            Integer.parseInt(cottageOwnerDTO.getPhoneNumber());
            Integer.parseInt(cottageOwnerDTO.getAddress().getPostalCode());
        }catch (NumberFormatException nfe){
            return new ResponseEntity<>("Phone number and postal code must be numbers.",HttpStatus.BAD_REQUEST);
        }
        cottageOwnerDTO.setRegistrationStatus(cottageOwner.getRegistrationStatus());
        CottageOwner updatedCottageOwner = mapper.map(cottageOwnerDTO,CottageOwner.class);
        updatedCottageOwner.setRoles(cottageOwner.getRoles());
        updatedCottageOwner.setMainPhoto(cottageOwner.getMainPhoto());
        updatedCottageOwner.setPoints(cottageOwner.getPoints());
        updatedCottageOwner.setEnabled(cottageOwner.isEnabled());
        updatedCottageOwner.setRegistrationReason(cottageOwner.getRegistrationReason());
        updatedCottageOwner.setType(cottageOwner.getType());
        cottageOwnerRepository.save(updatedCottageOwner);
        return new ResponseEntity<>("Successfully edited.",HttpStatus.OK);
    }


    public CottageOwner save(UserRequest userRequest) {
        CottageOwner c = new CottageOwner();
        UserService.setUserInfo(userRequest, c, passwordEncoder);
        c.setPhoneNumber(userRequest.getPhoneNumber());
        c.setPoints(0);
        c.setRegistrationStatus(RequestStatus.ON_HOLD);
        c.setRegistrationReason(userRequest.getReason());
        List<Role> roles = roleService.findByName(userRequest.getRole());
        c.setRoles(roles);

        return this.cottageOwnerRepository.save(c);
    }
}
