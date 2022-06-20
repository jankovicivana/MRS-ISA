package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.ClientDTO;
import ftn.mrs.isa.rentalapp.dto.ReportCreateDTO;
import ftn.mrs.isa.rentalapp.dto.ReportDTO;
import ftn.mrs.isa.rentalapp.model.reservation.Report;
import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import ftn.mrs.isa.rentalapp.model.user.Advertiser;
import ftn.mrs.isa.rentalapp.model.user.AdvertiserReview;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.service.*;
import ftn.mrs.isa.rentalapp.util.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


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

    @Autowired
    private TokenUtils tokenUtils;

    @GetMapping(value = "/activate/{token}" )
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<String> activate(@PathVariable String token){
        String username = tokenUtils.getUsernameFromToken(token);
        return clientService.activate(username);
    }

    @GetMapping(value = "/all")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<ClientDTO>> getAllClients(Principal principal){
        return clientService.findAll();
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
    public ResponseEntity<ClientDTO> updateClient(@RequestBody ClientDTO clientDTO, Principal principal){
        return clientService.updateClient(clientDTO, principal.getName());
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> delete(@PathVariable Integer id,Principal principal){
        return clientService.delete(id);
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
    @PreAuthorize("hasAnyRole('cottageOwner','boatOwner','fishingInstructor')")
    public ResponseEntity<String> createReport(@RequestBody ReportCreateDTO reportDTO,Principal principal){
        Client client = clientService.findOne(reportDTO.getClientId());
        Advertiser advertiser = cottageOwnerService.findByEmail(principal.getName());
        if(advertiser == null){
            advertiser = boatOwnerService.findByEmail(principal.getName());
        }
        if(advertiser == null){
            advertiser = fishingInstructorService.findByEmail(principal.getName());
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


    @GetMapping(value = "/getReportOnHold")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<ReportDTO>> getReportOnHold(Principal principal){
        List<Report> reports = reportService.getReportsOnHold();
        List<ReportDTO> reportDTOS = new ArrayList<>();
        for(Report r : reports){
            reportDTOS.add(mapper.map(r, ReportDTO.class));
        }
        return new ResponseEntity<>(reportDTOS, HttpStatus.OK);
    }

    @PostMapping(value = "/acceptPenalty")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> acceptPenaltyReport(@RequestBody ReportDTO reportDTO, Principal principal) throws MessagingException, InterruptedException {
        if (clientService.answerPenalty(reportDTO.getId(), RequestStatus.ACCEPTED)) {
            return new ResponseEntity<>("Accepting is successful.",HttpStatus.OK);
        }
        return new ResponseEntity<>("Accepting is denied.",HttpStatus.BAD_REQUEST);

    }

    @PostMapping(value = "/rejectPenalty")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> rejectPenaltyReport(@RequestBody ReportDTO reportDTO, Principal principal) throws MessagingException, InterruptedException {
        if (clientService.answerPenalty(reportDTO.getId(), RequestStatus.REJECTED)) {
            return new ResponseEntity<>("Rejecting is successful.",HttpStatus.OK);
        }
        return new ResponseEntity<>("Rejecting is denied.",HttpStatus.BAD_REQUEST);
    }
}
