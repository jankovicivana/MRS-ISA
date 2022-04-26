package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.CottageDTO;
import ftn.mrs.isa.rentalapp.dto.CottageOwnerDTO;
import ftn.mrs.isa.rentalapp.dto.FishingInstructorDTO;
import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.model.user.CottageOwner;
import ftn.mrs.isa.rentalapp.model.user.FishingInstructor;
import ftn.mrs.isa.rentalapp.service.CottageOwnerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/cottageOwner")
public class CottageOwnerController {

    @Autowired
    CottageOwnerService cottageOwnerService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(value = "/{id}")
    public ResponseEntity<CottageOwnerDTO> getInstructor(@PathVariable Integer id){
        CottageOwner cottageOwner = cottageOwnerService.findOne(id);
        if(cottageOwner == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mapper.map(cottageOwner,CottageOwnerDTO.class), HttpStatus.OK);
    }



    @PostMapping(value = "/updateCottageOwner" )
    public ResponseEntity<CottageOwnerDTO> updateCottageOwner(@RequestBody CottageOwnerDTO cottageOwnerDTO) {
        CottageOwner cottageOwner = cottageOwnerService.findOne(cottageOwnerDTO.getId());
        if(cottageOwner == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        cottageOwner.setName(cottageOwnerDTO.getName());
        cottageOwner.setSurname(cottageOwnerDTO.getSurname());
        cottageOwner.setPhoneNumber(cottageOwnerDTO.getPhoneNumber());
        Address a = mapper.map(cottageOwnerDTO.getAddress(),Address.class);
        cottageOwner.setAddress(a);
        cottageOwnerService.updateCottageOwner(cottageOwner);
        return new ResponseEntity<>(mapper.map(cottageOwner,CottageOwnerDTO.class),HttpStatus.OK);
    }

}
