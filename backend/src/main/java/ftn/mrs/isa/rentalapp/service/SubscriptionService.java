package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.dto.SubscriptionDTO;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import ftn.mrs.isa.rentalapp.model.entity.Subscription;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.repository.SubscriptionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired ClientService clientService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private EntityService entityService;


    public ResponseEntity<List<SubscriptionDTO>> getSubscriptionsByUser(String mail) {
        Client client = clientService.findByEmail(mail);
        List<Subscription> subs = subscriptionRepository.findAllByClientId(client.getId());
        List<SubscriptionDTO> subsDTO = new ArrayList<>();
        for(Subscription s: subs){
            SubscriptionDTO subDTO = mapper.map(s, SubscriptionDTO.class);
            subsDTO.add(subDTO);
        }
        return new ResponseEntity<>(subsDTO, HttpStatus.OK);
    }

    public Subscription findOne(Integer id) {
        return subscriptionRepository.findById(id).orElse(null);
    }


    public ResponseEntity<String> deleteSubscription(Integer id) {
        Subscription sub = findOne(id);
        if(sub == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        subscriptionRepository.delete(sub);
        return new ResponseEntity<>("Deletion is successful.",HttpStatus.OK);
    }

    public void save(Subscription s){ subscriptionRepository.save(s); }

    public ResponseEntity<String> addSubscription(Integer id, String mail) { // provjere
        Client c = clientService.findByEmail(mail);
        EntityType e = entityService.findOne(id);
        for(Subscription sub: c.getSubscriptions()){
            if(sub.getEntity().getId().equals(e.getId())){
                return new ResponseEntity<>("Already subscribed", HttpStatus.OK);
            }
        }
        Subscription s = new Subscription();
        s.setEntity(e);
        s.setClient(c);
        subscriptionRepository.save(s);
        return new ResponseEntity<>("Successfully subscribed", HttpStatus.OK);
    }
}
