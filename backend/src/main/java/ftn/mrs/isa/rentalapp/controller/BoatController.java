package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.BoatCreateDTO;
import ftn.mrs.isa.rentalapp.dto.BoatDTO;
import ftn.mrs.isa.rentalapp.model.entity.*;
import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/boats")
public class BoatController {

    @Autowired
    private BoatService boatService;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private NavigationEquipmentService navigationEquipmentService;

    @Autowired
    private AdditionalServiceService additionalServiceService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(value = "/all")
    public ResponseEntity<List<BoatDTO>> getAllBoats(){
        List<Boat> boats = boatService.findAll();

        List<BoatDTO> boatsDTO = new ArrayList<>();
        for (Boat b : boats){
            boatsDTO.add(mapper.map(b, BoatDTO.class));
        }

        return new ResponseEntity<>(boatsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/allByOwner")
    @PreAuthorize("hasRole('boatOwner')")
    public ResponseEntity<List<BoatDTO>> getAllBoatsByOwner(Principal principal){
        List<Boat> boats = boatService.findAllByOwnerEmail(principal.getName());

        List<BoatDTO> boatsDTO = new ArrayList<>();
        for (Boat b : boats){
            boatsDTO.add(mapper.map(b, BoatDTO.class));
        }

        return new ResponseEntity<>(boatsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BoatDTO> getBoat(@PathVariable Integer id){
        Boat boat = boatService.findOne(id);
        if(boat == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BoatDTO b = mapper.map(boat,BoatDTO.class);
        b.setType(BoatType.toString(boat.getType()));
        return new ResponseEntity<>(b,HttpStatus.OK);
    }

    @PostMapping("/addBoat")
    @PreAuthorize("hasRole('boatOwner')")
    public ResponseEntity<BoatDTO> addBoat(@RequestBody BoatCreateDTO boatCreateDTO,Principal principal) throws IOException {
        Boat boat = mapper.map(boatCreateDTO,Boat.class);
        boat.setAddress(new Address(boatCreateDTO.getStreet(),boatCreateDTO.getCity(),boatCreateDTO.getPostal_code(),boatCreateDTO.getCountry()));
        boat.setType(BoatType.getTypeFromString(boatCreateDTO.getType()));
        boat.setKind(EntityKind.BOAT);

        Set<Rule> rules = ruleService.createRuleFromString(boatCreateDTO.getRules(),boat);
        boat.setRules(rules);

        Set<FishingEquipment> fishingEquipments = equipmentService.createFishingEquipmentFromString(boatCreateDTO.getFishingEquipment(),boat);
        boat.setFishingEquipment(fishingEquipments);

        Set<AdditionalService> services = additionalServiceService.createAddServiceFromString(boatCreateDTO.getAdditionalServices(),boat);
        boat.setAdditionalServices(services);

        Set<Image> images = imageService.createImageFromString(boatCreateDTO.getImages(),boat);
        boat.setImages(images);

        Set<NavigationEquipment> navigationEquipments = navigationEquipmentService.createNavigationEquipmentFromString(boatCreateDTO.getNavigationEquipment(),boat);
        boat.setNavigationEquipment(navigationEquipments);

        boatService.save(boat);

        equipmentService.addFishingEquipments(fishingEquipments);
        navigationEquipmentService.addNavigationEquipments(navigationEquipments);
        ruleService.addRules(rules);
        additionalServiceService.addAdditionalServices(services);
        imageService.addImages(images);

        return new ResponseEntity<>(mapper.map(boat,BoatDTO.class),HttpStatus.CREATED);
    }

    @PutMapping("/updateBoat")
    @PreAuthorize("hasRole('boatOwner')")
    public ResponseEntity<BoatDTO> updateBoat(@RequestBody BoatDTO boatDTO,Principal principal){
        Boat boat = boatService.findOne(boatDTO.getId());
        if(boat == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        boat.setName(boatDTO.getName());
        boat.setPrice(boatDTO.getPrice());
        boat.setCapacity(boatDTO.getCapacity());
        boat.setDescription(boatDTO.getDescription());
        boat.setMaxSpeed(boatDTO.getMaxSpeed());
        boat.setLength(boatDTO.getLength());
        boat.setPower(boatDTO.getPower());
        boat.setMotorNum(boatDTO.getMotorNum());
        boat.setCancelFee(boatDTO.getCancelFee());
        Address a = mapper.map(boatDTO.getAddress(),Address.class);
        boat.setAddress(a);
        boatService.save(boat);
        return new ResponseEntity<>(mapper.map(boat,BoatDTO.class),HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteBoat/{id}")
    @PreAuthorize("hasRole('boatOwner')")
    public ResponseEntity<String> deleteBoat(@PathVariable Integer id,Principal principal){
        Boat boat = boatService.findOne(id);
        if(boat == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(!boatService.canDeleteBoat(boat)){
            return new ResponseEntity<>("Boat has reservations.Deletion is not possible.",HttpStatus.BAD_REQUEST);
        }
        boatService.deleteBoat(boat);
        return new ResponseEntity<>("Deletion is successful.",HttpStatus.OK);
    }

    @GetMapping(value = "/getAverageGrade")
    @PreAuthorize("hasRole('boatOwner')")
    public ResponseEntity<Double> getBoatGrade(Principal principal){
        List<Boat> boats = boatService.findAllByOwnerEmail(principal.getName());
        double averageGrade = 0.0;
        for (Boat b : boats){
            averageGrade += b.getAverageGrade();
        }
        averageGrade = averageGrade / boats.size();
        return new ResponseEntity<>(averageGrade, HttpStatus.OK);
    }

}
