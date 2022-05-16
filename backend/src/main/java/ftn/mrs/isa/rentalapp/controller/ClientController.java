package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.ClientDTO;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(value = "/all")
    public ResponseEntity<List<ClientDTO>> getAllClients(){
        List<Client> clients = clientService.findAll();
        List<ClientDTO> clientsDTO = new ArrayList<>();
        for(Client c : clients){
            clientsDTO.add(mapper.map(c, ClientDTO.class));
        }
        return new ResponseEntity<>(clientsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Integer id){
        Client client = clientService.findOne(id);
        if(client == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mapper.map(client, ClientDTO.class), HttpStatus.OK);
    }

    @PostMapping(value = "/updateClient" )
    public ResponseEntity<ClientDTO> updateClient(ClientDTO clientDTO){
        Client client = clientService.findOne(clientDTO.getId());
        if(client == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        clientService.updateClient(mapper.map(clientDTO, Client.class));
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        Client client = clientService.findOne(id);
        if(client == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!clientService.canDeleteClient(client)){
            return new ResponseEntity<>("Client has reservations.Deletion is not possible.",HttpStatus.OK);
        }
        clientService.deleteClient(client);
        return new ResponseEntity<>("Deletion is successful.",HttpStatus.OK);
    }

    /*@GetMapping(value = "/subscriptions/{id}")
    public ResponseEntity<SubscriptionDTO> getSubscriptions(@PathVariable Integer id){
        Client client = clientService.findOne(id);
        List<SubscriptionDTO> subscriptionsDTO = new ArrayList<>();
        for(Subscription s: client.getSubscriptions()){
            subscriptionsDTO.add(new SubscriptionDTO(s));
        }
        return new ResponseEntity<>(subscriptionsDTO, HttpStatus.OK);
    }*/

}
