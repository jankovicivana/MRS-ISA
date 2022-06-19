package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.dto.UserRequest;
import ftn.mrs.isa.rentalapp.model.reservation.Report;
import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.model.user.Role;
import ftn.mrs.isa.rentalapp.repository.AddressRepository;
import ftn.mrs.isa.rentalapp.repository.ClientRepository;
import ftn.mrs.isa.rentalapp.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ReservationRepository reservationsRepository;


    @Autowired
    private RoleService roleService;


    @Autowired
    private ReportService reportService;


    @Autowired
    private EmailService emailService;


    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private AddressRepository addressRepository;

    public List<Client> findAll() {return clientRepository.findAll(); }

    public Client findOne(Integer id) {return clientRepository.findById(id).orElse(null);}

    public Client findByEmail(String string) {return clientRepository.findByEmail(string);}

    public void updateClient(Client client){
        clientRepository.save(client);
    }
    public void save(Client client){clientRepository.save(client);}

    public Client save(UserRequest userRequest) {
        Client c = new Client();
        c.setEmail(userRequest.getEmail());
        c.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        c.setName(userRequest.getName());
        c.setSurname(userRequest.getSurname());
        c.setAddress(new Address(userRequest.getStreet(), userRequest.getCity(), userRequest.getPostalCode(), userRequest.getCountry()));
        c.setEnabled(false);
        c.setPhoneNumber(userRequest.getPhoneNumber());
        c.setPoints(0);
        c.setPenalties(0);
        List<Role> roles = roleService.findByName(userRequest.getRole());
        c.setRoles(roles);

        return this.clientRepository.save(c);
    }


    public boolean canDeleteClient(Client client) {
        List<Reservation> l = reservationsRepository.getReservationByClient(LocalDate.now(), client.getId());
        return l.size() == 0;
    }

    public void deleteClient(Client client) {
        client.setDeleted(true);
        clientRepository.save(client);
        for(Reservation r : client.getReservations()){
            r.setDeleted(true);
        }
        reservationsRepository.saveAll(client.getReservations());
    }

    public boolean answerPenalty(Integer id, RequestStatus status) throws MessagingException, InterruptedException {
        try{Report report = reportService.findOne(id);
        report.setPenaltyStatus(status);
        String message;
        Client c = findOne(report.getClient().getId());
        if (status == RequestStatus.ACCEPTED){
            c.setPenalties(c.getPenalties()+1);
            save(c);
            message = "accepted";
        }
        else{
            message = "rejected";
        }
        reportService.save(report);
        emailService.sendNotificationReportToClientAsync(c,report.getAdvertiser(),message);
        emailService.sendNotificationReportToAdvertiserAsync(c, report.getAdvertiser(),message);}
        catch (PessimisticLockingFailureException p){
            return false;
        }
        return true;
    }
}
