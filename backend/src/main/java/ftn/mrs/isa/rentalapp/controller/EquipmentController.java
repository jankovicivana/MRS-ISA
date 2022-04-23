package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.FishingEquipmentDTO;
import ftn.mrs.isa.rentalapp.model.entity.Adventure;
import ftn.mrs.isa.rentalapp.model.entity.FishingEquipment;
import ftn.mrs.isa.rentalapp.service.AdventureService;
import ftn.mrs.isa.rentalapp.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private AdventureService adventureService;

    @Autowired
    private ModelMapper mapper;


    @PostMapping("/addFishingEquipment")
    public ResponseEntity<FishingEquipmentDTO> addFishingEquipment(@RequestBody FishingEquipmentDTO fishingEquipmentDTO){
        System.out.print("idee");
        if(fishingEquipmentDTO.getAdventureId() == null && fishingEquipmentDTO.getBoatId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Adventure adventure = adventureService.findOne(fishingEquipmentDTO.getAdventureId());
        if(adventure == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        FishingEquipment equip = new FishingEquipment();
        equip.setEquipment(fishingEquipmentDTO.getEquipment());
        equip.setAdventure(adventure);
        adventure.getFishingEquipment().add(equip);

        equipmentService.saveFishingEquipment(equip);
        return new ResponseEntity<>(mapper.map(equip, FishingEquipmentDTO.class),HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/deleteFishingEquipment/{id}")
    public  ResponseEntity<Void> deleteFishingEquipment(@PathVariable Integer id){
        FishingEquipment equip = equipmentService.findFishingEquipment(id);

        if(equip != null){
            equipmentService.removeFishingEquipment(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
