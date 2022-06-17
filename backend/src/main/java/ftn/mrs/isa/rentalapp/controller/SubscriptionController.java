package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.SubscriptionDTO;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import ftn.mrs.isa.rentalapp.model.entity.Subscription;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.service.ClientService;
import ftn.mrs.isa.rentalapp.service.EntityService;
import ftn.mrs.isa.rentalapp.service.SubscriptionService;
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
@RequestMapping("api/sub")
public class SubscriptionController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EntityService entityService;

    @GetMapping(value = "/getSubscriptions")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptions(Principal principal){
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        String mail = principal.getName();
        Client client = clientService.findByEmail(mail);
        List<Subscription> subs = subscriptionService.getSubscriptionsByUser(client.getId());
        List<SubscriptionDTO> subsDTO = new ArrayList<>();
        for(Subscription s: subs){
            SubscriptionDTO subDTO = mapper.map(s, SubscriptionDTO.class);
            subsDTO.add(subDTO);
        }
        return new ResponseEntity<>(subsDTO, HttpStatus.OK);
    }

    @GetMapping(value="/subscribe/{id}")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<String> subscribe(@PathVariable Integer id, Principal principal) {
        Client c = clientService.findByEmail(principal.getName());
        EntityType e = entityService.findOne(id);
        for(Subscription sub: c.getSubscriptions()){
            if(sub.getEntity().getId().equals(e.getId())){
                return new ResponseEntity<>("Already subscribed", HttpStatus.OK);
            }
        }
        Subscription s = new Subscription();
        s.setEntity(e);  // fale neke provjere
        s.setClient(c);
        subscriptionService.save(s);
        return new ResponseEntity<>("Successfully subscribed", HttpStatus.OK);
    }

    @GetMapping(value="/canSubscribe/{id}")
    @PreAuthorize("hasRole('client')")
    public Boolean canSubscribe(@PathVariable Integer id, Principal principal) {
        Client c = clientService.findByEmail(principal.getName());
        EntityType e = entityService.findOne(id);
        for(Subscription s: c.getSubscriptions()){
            if(s.getEntity().getId().equals(e.getId())){
                return false;
            }
        }
        return true;
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        Subscription sub = subscriptionService.findOne(id);
        if(sub == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        subscriptionService.deleteSubscription(sub);
        return new ResponseEntity<>("Deletion is successful.",HttpStatus.OK);
    }
}
