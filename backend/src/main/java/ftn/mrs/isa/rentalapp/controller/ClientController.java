package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.ClientDTO;
import ftn.mrs.isa.rentalapp.dto.ReportDTO;
import ftn.mrs.isa.rentalapp.model.reservation.Report;
import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import ftn.mrs.isa.rentalapp.model.user.Advertiser;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    @Autowired
    private CottageOwnerService cottageOwnerService;

    @Autowired
    private BoatOwnerService boatOwnerService;

    @Autowired
    private FishingInstructorService fishingInstructorService;

    @Autowired
    private ReportService reportService;


    @GetMapping(value = "/all")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<ClientDTO>> getAllClients(Principal principal){
        List<Client> clients = clientService.findAll();
        List<ClientDTO> clientsDTO = new ArrayList<>();
        for(Client c : clients){
            if (!c.isDeleted()){
            clientsDTO.add(mapper.map(c, ClientDTO.class));
            }
        }
        return new ResponseEntity<>(clientsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/getClient")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<ClientDTO> getClient(Principal principal){
        String mail = principal.getName();
        Client client = clientService.findByEmail(mail);
        if(client == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mapper.map(client, ClientDTO.class), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('cottageOwner','boatOwner','fishingInstructor')")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Integer id,Principal principal){
        Client client = clientService.findOne(id);
        if(client == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mapper.map(client, ClientDTO.class), HttpStatus.OK);
    }

    @PostMapping(value = "/updateClient" )
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<ClientDTO> updateClient(@RequestBody ClientDTO clientDTO){
        Client client = clientService.findOne(clientDTO.getId());
        if(client == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        clientService.updateClient(mapper.map(clientDTO, Client.class));
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> delete(@PathVariable Integer id,Principal principal){
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

    //ova ne treba funkcija
    @PostMapping(value = "/addPenalty/{id}")
    public ResponseEntity<String> addPenalty(@PathVariable Integer id){
        Client client = clientService.findOne(id);
        if(client == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        client.setPenalties(client.getPenalties()+1);
        clientService.save(client);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/createReport")
    @PreAuthorize("hasAnyRole('cottageOwner','boatOwner')")
    public ResponseEntity<String> createReport(@RequestBody ReportDTO reportDTO){
        Client client = clientService.findOne(reportDTO.getClientId());
        Advertiser advertiser = cottageOwnerService.findOne(reportDTO.getAdvertiserId());
        if(advertiser == null){
            advertiser = boatOwnerService.findOne(reportDTO.getAdvertiserId());
        }
        if(advertiser == null){
            advertiser = fishingInstructorService.findOne(reportDTO.getAdvertiserId());
        }
        if(advertiser == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(client == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Report r = new Report();
        r.setClient(client);
        r.setAdvertiser(advertiser);
        if(!reportDTO.getDidShowUp()){
            r.setClientShowedUp(false);
            r.setPenaltyStatus(RequestStatus.ACCEPTED);
            client.setPenalties(client.getPenalties()+1);
            clientService.save(client);
        }else {
            r.setReportString(reportDTO.getReportString());
            r.setClientShowedUp(true);
            if(reportDTO.getSanction()){
                r.setPenaltyStatus(RequestStatus.ON_HOLD);
            }else{
                r.setPenaltyStatus(RequestStatus.REJECTED);
            }
        }

        reportService.save(r);

        return new ResponseEntity<>(HttpStatus.OK);
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
