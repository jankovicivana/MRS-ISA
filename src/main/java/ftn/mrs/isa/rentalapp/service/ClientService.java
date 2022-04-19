package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.dto.ClientDTO;
import ftn.mrs.isa.rentalapp.model.entity.AdditionalService;
import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.repository.AddressRepository;
import ftn.mrs.isa.rentalapp.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    public List<Client> findAll() {return clientRepository.findAll(); }

    public Client findOne(Integer id) {return clientRepository.findById(id).orElse(null);}

    public void updateClient(ClientDTO clientDTO){
        Client client = clientRepository.findById(clientDTO.getId()).orElse(null);
        // if null vrati gresku
        client.setName(clientDTO.getName());
        client.setSurname(clientDTO.getSurname());
        client.setPassword(clientDTO.getPassword());
        Address a = client.getAddress();
        a.setCountry(clientDTO.getCountry());
        a.setCity(clientDTO.getCity());
        a.setStreet(clientDTO.getStreet());
        a.setPostalCode(clientDTO.getPostalCode());
        addressRepository.save(a);
        clientRepository.save(client);
    }
}
