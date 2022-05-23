package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.dto.UserRequest;
import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.model.user.CottageOwner;
import ftn.mrs.isa.rentalapp.model.user.Role;
import ftn.mrs.isa.rentalapp.repository.CottageOwnerRepository;
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

    public CottageOwner findOne(Integer id){return cottageOwnerRepository.findById(id).orElse(null);}

    public CottageOwner findByEmail(String email){return  cottageOwnerRepository.findByEmail(email);}

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
