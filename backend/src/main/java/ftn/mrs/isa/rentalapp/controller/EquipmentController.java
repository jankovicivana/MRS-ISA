package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.FishingEquipmentDTO;
import ftn.mrs.isa.rentalapp.model.entity.Adventure;
import ftn.mrs.isa.rentalapp.model.entity.Boat;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import ftn.mrs.isa.rentalapp.model.entity.FishingEquipment;
import ftn.mrs.isa.rentalapp.service.AdventureService;
import ftn.mrs.isa.rentalapp.service.BoatService;
import ftn.mrs.isa.rentalapp.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private AdventureService adventureService;

    @Autowired
    private BoatService boatService;

    @Autowired
    private ModelMapper mapper;


    @PostMapping("/addFishingEquipment")
    @PreAuthorize("hasRole('fishingInstructor')")
    public ResponseEntity<FishingEquipmentDTO> addFishingEquipment(@RequestBody FishingEquipmentDTO fishingEquipmentDTO, Principal principal){
        System.out.print("idee");
        if(fishingEquipmentDTO.getAdventureId() == null && fishingEquipmentDTO.getBoatId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        EntityType entity = adventureService.findOne(fishingEquipmentDTO.getAdventureId());
        if(entity == null){
            entity = boatService.findOne(fishingEquipmentDTO.getBoatId());
        }
        if(entity == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        FishingEquipment equip = new FishingEquipment();
        equip.setEquipment(fishingEquipmentDTO.getEquipment());
        if(entity instanceof Adventure){
            equip.setAdventure((Adventure) entity);
            ((Adventure) entity).getFishingEquipment().add(equip);
        }else{
            equip.setBoat((Boat) entity);
            ((Boat) entity).getFishingEquipment().add(equip);
        }

        equipmentService.saveFishingEquipment(equip);
        return new ResponseEntity<>(mapper.map(equip, FishingEquipmentDTO.class),HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/deleteFishingEquipment/{id}")
    @PreAuthorize("hasRole('fishingInstructor')")
    public  ResponseEntity<Void> deleteFishingEquipment(@PathVariable Integer id, Principal principal){
        FishingEquipment equip = equipmentService.findFishingEquipment(id);

        if(equip != null){
            equipmentService.removeFishingEquipment(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
