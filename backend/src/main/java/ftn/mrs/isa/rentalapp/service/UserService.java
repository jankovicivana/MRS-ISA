package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.dto.AccountDeleteRequestDTO;
import ftn.mrs.isa.rentalapp.dto.UserRequest;
import ftn.mrs.isa.rentalapp.model.entity.Adventure;
import ftn.mrs.isa.rentalapp.model.entity.Boat;
import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import ftn.mrs.isa.rentalapp.model.user.*;
import ftn.mrs.isa.rentalapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.swing.text.html.parser.Entity;
import java.util.List;

@Service
@Transactional
public class UserService  implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;


    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Autowired
    private RoleService roleService;

    @Autowired
    private AdvertiserRepository advertiserRepository;

    @Autowired
    private AdventureService adventureService;

    @Autowired
    private BoatService boatService;

    @Autowired
    private CottageService cottageService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private  DeleteAccountRequestRepository deleteAccountRequestRepository;

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

    public List<AccountDeleteRequest> getDeleteRequestOnHold() {
        return deleteAccountRequestRepository.getOnHold();
    }

    public void saveDeletionRequest(AccountDeleteRequest request) {
        deleteAccountRequestRepository.save(request);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User findUserByEmail(String email) {
         return userRepository.findByEmail(email);
    }

    public User findUserByEntity(EntityType entity) {
        User u = null;
        System.out.print(entity.getClass());
        Adventure i = adventureService.findOne(entity.getId());
        if(i!= null){
            u = i.getFishingInstructor();
        }
        Cottage c = cottageService.findOne(entity.getId());
        if(c!= null){
            u = c.getCottageOwner();
        }
        Boat b = boatService.findOne(entity.getId());
        if(b!= null){
            u = b.getBoatOwner();
        }

        return u;
    }

    public void setPoints(User user, int i) {
        user.setPoints(i);
        saveUser(user);
    }

    public boolean setDeletionStatus(AccountDeleteRequestDTO accountDeleteRequestDTO, RequestStatus status) throws MessagingException {
        AccountDeleteRequest request = deleteAccountRequestRepository.getDeletionRequest(accountDeleteRequestDTO.getId());
        if (request.getStatus()!=RequestStatus.ON_HOLD){
            return false;
        }
        request.setAnswer(accountDeleteRequestDTO.getAnswer());
        request.setStatus(status);
        saveDeletionRequest(request);
        User user = findUserByEmail(accountDeleteRequestDTO.getUser().getEmail());
        String s;
        if (status.equals(RequestStatus.ACCEPTED)){
            user.setDeleted(true);
            s = "accepted";
        }else{
            user.setDeleted(false);
            s = "rejected";
        }
        saveUser(user);
        emailService.sendDeletionProfileNotification(user,s);
        return true;
    }
}