package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.SubscriptionDTO;
import ftn.mrs.isa.rentalapp.model.entity.Subscription;
import ftn.mrs.isa.rentalapp.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/findByUser/{id}")
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptionsByUser(@PathVariable Integer id){
        List<Subscription> subs = subscriptionService.getSubscriptionsByUser(id);
        List<SubscriptionDTO> subsDTO = new ArrayList<>();
        for(Subscription s: subs){
            SubscriptionDTO subDTO = mapper.map(s, SubscriptionDTO.class);
            subsDTO.add(subDTO);
        }
        return new ResponseEntity<>(subsDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        Subscription sub = subscriptionService.findOne(id);
        if(sub == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        subscriptionService.deleteSubscription(sub);
        return new ResponseEntity<>("Deletion is successful.",HttpStatus.OK);
    }

}
