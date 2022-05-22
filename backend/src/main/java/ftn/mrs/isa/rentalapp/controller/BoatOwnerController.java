package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.BoatOwnerDTO;
import ftn.mrs.isa.rentalapp.model.user.BoatOwner;
import ftn.mrs.isa.rentalapp.service.BoatOwnerService;
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
@RequestMapping("api/boatOwner")
public class BoatOwnerController {

    @Autowired
    private BoatOwnerService boatOwnerService;

    @Autowired
    private ModelMapper mapper;

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> delete(@PathVariable Integer id, Principal principal){
        BoatOwner client = boatOwnerService.findOne(id);
        if(client == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!boatOwnerService.canDeleteBoatOwner(client)){
            return new ResponseEntity<>("Instructor has reservations.Deletion is not possible.",HttpStatus.OK);
        }
        boatOwnerService.deleteBoatOwner(client);
        return new ResponseEntity<>("Deletion is successful.",HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<BoatOwnerDTO>> getAllBoatOwners(Principal principal){
        List<BoatOwner> owners = boatOwnerService.findAll();
        List<BoatOwnerDTO> ownersDTO = new ArrayList<>();
        for(BoatOwner c : owners){
            ownersDTO.add(mapper.map(c,BoatOwnerDTO.class));
        }
        return new ResponseEntity<>(ownersDTO, HttpStatus.OK);
    }

}
