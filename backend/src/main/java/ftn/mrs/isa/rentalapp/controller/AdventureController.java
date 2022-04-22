package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.AdventureDTO;
import ftn.mrs.isa.rentalapp.dto.AdventureCreateDTO;
import ftn.mrs.isa.rentalapp.dto.QuickReservationDTO;
import ftn.mrs.isa.rentalapp.model.entity.*;
import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/adventures")
public class AdventureController {

    @Autowired
    private AdventureService adventureService;

   @Autowired
   private ModelMapper modelMapper;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private AdditionalServiceService additionalServiceService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/addAdventure")
    public ResponseEntity<AdventureDTO> addAdventure(@RequestBody AdventureCreateDTO adventureDTO) throws Exception {
        System.out.print("uslo u add");
        Adventure adventure = mapper.map(adventureDTO,Adventure.class);
        adventure.setAddress(new Address(adventureDTO.getStreet(),adventureDTO.getCity(),adventureDTO.getPostal_code(),adventureDTO.getCountry()));

        Set<Rule> rules = ruleService.createRuleFromString(adventureDTO.getRules(),adventure);
        System.out.print(rules.size());
        adventure.setRules(rules);

        Set<FishingEquipment> equipment = equipmentService.createFishingEquipmentFromString(adventureDTO.getFishingEquipment(),adventure);
        adventure.setFishingEquipment(equipment);

        Set<AdditionalService> services = additionalServiceService.createAddServiceFromString(adventureDTO.getAdditionalServices(),adventure);
        adventure.setAdditionalServices(services);

        Set<Image> images = imageService.createImageFromString(adventureDTO.getImages(),adventure);
        adventure.setImages(images);

        adventureService.save(adventure);

        return new ResponseEntity<>(mapper.map(adventure,AdventureDTO.class), HttpStatus.CREATED);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AdventureDTO> getAdventure(@PathVariable Integer id){
        Adventure adventure = adventureService.findOne(id);
        System.out.print("Doslo ovdje");
        if(adventure == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AdventureDTO dto = mapper.map(adventure,AdventureDTO.class);
        dto.setBiography(adventure.getFishingInstructor().getBiography());
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    }

