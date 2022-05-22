package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.CottageOwnerDTO;
import ftn.mrs.isa.rentalapp.model.user.CottageOwner;
import ftn.mrs.isa.rentalapp.service.CottageOwnerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/cottageOwner")
public class CottageOwnerController {

    @Autowired
    CottageOwnerService cottageOwnerService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(value = "/getCottageOwner")
    @PreAuthorize("hasRole('cottageOwner')")
    public ResponseEntity<CottageOwnerDTO> getCottageOwner(Principal principal){
        CottageOwner cottageOwner = cottageOwnerService.findByEmail(principal.getName());
        if(cottageOwner == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mapper.map(cottageOwner,CottageOwnerDTO.class), HttpStatus.OK);
    }



    @PostMapping(value = "/updateCottageOwner" )
    @PreAuthorize("hasRole('cottageOwner')")
    public ResponseEntity<CottageOwnerDTO> updateCottageOwner(@RequestBody CottageOwnerDTO cottageOwnerDTO) {
        CottageOwner cottageOwner = cottageOwnerService.findOne(cottageOwnerDTO.getId());
        if(cottageOwner == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        cottageOwnerDTO.setRegistrationStatus(cottageOwner.getRegistrationStatus());
        cottageOwnerService.updateCottageOwner(mapper.map(cottageOwnerDTO,CottageOwner.class));
        return new ResponseEntity<>(cottageOwnerDTO,HttpStatus.OK);
    }

}
