package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> findAll() {return clientRepository.findAll(); }

    public Client findOne(Integer id) {return clientRepository.findById(id).orElse(null);}

}
