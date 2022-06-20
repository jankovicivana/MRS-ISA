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
import java.util.List;

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
    public ResponseEntity<String> updateCottageOwner(@RequestBody CottageOwnerDTO cottageOwnerDTO,Principal principal) {
        return cottageOwnerService.updateCottageOwner(cottageOwnerDTO,principal.getName());
    }

    @GetMapping(value = "/all")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<CottageOwnerDTO>> getAllCottageOwners(Principal principal){
        List<CottageOwnerDTO> ownersDTO = cottageOwnerService.getAllCottageOwners();
        return new ResponseEntity<>(ownersDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> delete(@PathVariable Integer id, Principal principal){
        CottageOwner client = cottageOwnerService.findOne(id);
        if(client == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!cottageOwnerService.canDeleteCottageOwner(client)){
            return new ResponseEntity<>("Cottage owner has reservations.Deletion is not possible.",HttpStatus.BAD_REQUEST);
        }
        cottageOwnerService.deleteCottageOwner(client);
        return new ResponseEntity<>("Deletion is successful.",HttpStatus.OK);
    }
}
