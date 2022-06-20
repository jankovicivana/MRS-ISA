package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/penalty")
public class PenaltyController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientService clientService;

    @Scheduled(cron = "${greeting.cron}")
    public void cronJob() {
        logger.info("> cronJob");

        List<Client> clients = new ArrayList<Client>();
        clients = clientService.findAllClients();
        for(Client c: clients){
            c.setPenalties(0);
            clientService.save(c);
        }
        logger.info("< cronJob");
    }

}
