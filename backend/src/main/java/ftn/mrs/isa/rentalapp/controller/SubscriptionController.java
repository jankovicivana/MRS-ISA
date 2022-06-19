package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.SubscriptionDTO;
import ftn.mrs.isa.rentalapp.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/sub")
public class SubscriptionController {


    @Autowired
    private SubscriptionService subscriptionService;


    @GetMapping(value = "/getSubscriptions")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptions(Principal principal){
        return subscriptionService.getSubscriptionsByUser(principal.getName());
    }

    @GetMapping(value="/subscribe/{id}")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<String> subscribe(@PathVariable Integer id, Principal principal) {
        return subscriptionService.addSubscription(id, principal.getName());
    }


    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        return subscriptionService.deleteSubscription(id);
    }
}
