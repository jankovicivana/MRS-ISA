package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.NavigationEquipmentDTO;
import ftn.mrs.isa.rentalapp.model.entity.Boat;
import ftn.mrs.isa.rentalapp.model.entity.NavigationEquipment;
import ftn.mrs.isa.rentalapp.model.entity.NavigationEquipmentType;
import ftn.mrs.isa.rentalapp.service.BoatService;
import ftn.mrs.isa.rentalapp.service.NavigationEquipmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/navigationEquipment")
public class NavigationEquipmentController {

    @Autowired
    private NavigationEquipmentService navigationEquipmentService;

    @Autowired
    private BoatService boatService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/addNavigationEquipment")
    @PreAuthorize("hasRole('boatOwner')")
    public ResponseEntity<NavigationEquipmentDTO> addNavigationEquipment(@RequestBody NavigationEquipmentDTO navigationEquipmentDTO){
        if(navigationEquipmentDTO.getBoatId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Boat boat = boatService.findOne(navigationEquipmentDTO.getBoatId());
        if(boat == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        NavigationEquipment navigationEquipment = new NavigationEquipment();
        navigationEquipment.setEquipment(NavigationEquipmentType.getTypeFromString(navigationEquipmentDTO.getEquipment()));
        navigationEquipment.setBoat(boat);
        boat.getNavigationEquipment().add(navigationEquipment);

        navigationEquipmentService.saveNavigationEquipment(navigationEquipment);
        return new ResponseEntity<>(mapper.map(navigationEquipment,NavigationEquipmentDTO.class),HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/deleteNavigationEquipment/{id}")
    @PreAuthorize("hasRole('boatOwner')")
    public  ResponseEntity<Void> deleteNavigationEquipment(@PathVariable Integer id){
        NavigationEquipment equip = navigationEquipmentService.findNavigationEquipment(id);

        if(equip != null){
            navigationEquipmentService.removeNavigationEquipment(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
