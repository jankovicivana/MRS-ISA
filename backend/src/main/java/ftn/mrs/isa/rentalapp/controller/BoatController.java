package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.BoatCreateDTO;
import ftn.mrs.isa.rentalapp.dto.BoatDTO;
import ftn.mrs.isa.rentalapp.dto.CottageDTO;
import ftn.mrs.isa.rentalapp.model.entity.*;
import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<BoatDTO> getBoat(@PathVariable Integer id){
        Boat boat = boatService.findOne(id);
        if(boat == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(mapper.map(boat,BoatDTO.class),HttpStatus.OK);
    }

    @PostMapping("/addBoat")
    public ResponseEntity<BoatDTO> addBoat(@RequestBody BoatCreateDTO boatCreateDTO) throws IOException {
        Boat boat = mapper.map(boatCreateDTO,Boat.class);
        boat.setAddress(new Address(boatCreateDTO.getStreet(),boatCreateDTO.getCity(),boatCreateDTO.getPostal_code(),boatCreateDTO.getCountry()));
        boat.setType(BoatType.getTypeFromString(boatCreateDTO.getType()));

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

}
