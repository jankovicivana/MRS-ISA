package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.BoatDTO;
import ftn.mrs.isa.rentalapp.dto.CottageDTO;
import ftn.mrs.isa.rentalapp.model.entity.Boat;
import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.service.BoatService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/boats")
public class BoatController {

    @Autowired
    private BoatService boatService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(value = "/{id}")
    public ResponseEntity<BoatDTO> getBoat(@PathVariable Integer id){
        System.out.println(id+"**********");
        Boat boat = boatService.findOne(id);
        if(boat == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(mapper.map(boat,BoatDTO.class),HttpStatus.OK);
    }

}
