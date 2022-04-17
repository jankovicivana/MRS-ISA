package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.AdventureDTO;
import ftn.mrs.isa.rentalapp.model.entity.*;
import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/adventures")
public class AdventureController {

    @Autowired
    private  AdventureService adventureService;

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
    public ResponseEntity<AdventureDTO> addAdventure(@RequestBody AdventureDTO adventureDTO) throws Exception {
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












    public String encode (String payload){
        payload = payload.replace("%7B","{");
        payload = payload.replace("%22","\"");
        payload = payload.replace("%3A",":");
        payload = payload.replace("%7D=","}");
        payload = payload.replace("%2C",",");
        return payload;
    }
}
