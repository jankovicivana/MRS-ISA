package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.dto.ClientDTO;
import ftn.mrs.isa.rentalapp.dto.CottageDTO;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.repository.AddressRepository;
import ftn.mrs.isa.rentalapp.repository.ClientRepository;
import ftn.mrs.isa.rentalapp.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ReservationRepository reservationsRepository;

    @Autowired
    private AddressRepository addressRepository;

    public List<Client> findAll() {return clientRepository.findAll(); }

    public Client findOne(Integer id) {return clientRepository.findById(id).orElse(null);}

    public Client findByEmail(String string) {return clientRepository.findByEmail(string);}

    public void updateClient(Client client){
        clientRepository.save(client);
    }
    public void save(Client client){clientRepository.save(client);}



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
}
