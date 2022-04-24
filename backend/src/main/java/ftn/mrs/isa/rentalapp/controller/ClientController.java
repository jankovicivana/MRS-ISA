package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.ClientDTO;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.service.ClientService;
import lombok.RequiredArgsConstructor;
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


    @GetMapping(value = "/all")
    public ResponseEntity<List<ClientDTO>> getAllClients(){
        List<Client> clients = clientService.findAll();
        List<ClientDTO> clientsDTO = new ArrayList<>();
        for(Client c : clients){
            clientsDTO.add(new ClientDTO(c));
        }
        return new ResponseEntity<>(clientsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Integer id){
        Client client = clientService.findOne(id);
        if(client == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ClientDTO(client), HttpStatus.OK);
    }

    @PostMapping(value = "/updateClient" )
    public void updateClient(@RequestBody ClientDTO clientDTO) {
        // ***
        clientDTO.setId(2);
        clientService.updateClient(clientDTO);
    }

}
