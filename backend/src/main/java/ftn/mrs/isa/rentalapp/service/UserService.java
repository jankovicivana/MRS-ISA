package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.dto.UserRequest;
import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.model.user.Advertiser;
import ftn.mrs.isa.rentalapp.model.user.Role;
import ftn.mrs.isa.rentalapp.model.user.User;
import ftn.mrs.isa.rentalapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService  implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;


    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Autowired
    private RoleService roleService;

    @Autowired
    private AdvertiserRepository advertiserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    public Advertiser findAdvertiserById(Integer id) {
        return advertiserRepository.findById(id).orElse(null);
    }

    public User save(UserRequest userRequest) {
        User u = new User();
        u.setEmail(userRequest.getEmail());
        u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        u.setName(userRequest.getName());
        u.setSurname(userRequest.getSurname());
        u.setAddress(new Address(userRequest.getStreet(), userRequest.getCity(), userRequest.getPostalCode(), userRequest.getCountry()));
        u.setEnabled(true);
        u.setEmail(userRequest.getEmail());
        u.setPhoneNumber(userRequest.getPhoneNumber());

        //
        List<Role> roles = roleService.findByName(userRequest.getRole());
        u.setRoles(roles);

        return this.userRepository.save(u);
    }

    public List<Advertiser> getAdvertisersOnHold() {
        return advertiserRepository.getOnHold();
    }

    public void saveAdvertiser(Advertiser a) {
        advertiserRepository.save(a);
    }
}