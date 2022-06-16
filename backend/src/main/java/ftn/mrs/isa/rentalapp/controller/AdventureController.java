package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.AdventureCreateDTO;
import ftn.mrs.isa.rentalapp.dto.AdventureDTO;
import ftn.mrs.isa.rentalapp.model.entity.*;
import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.model.user.FishingInstructor;
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
@RequestMapping("api/adventures")
public class AdventureController {

    @Autowired
    private AdventureService adventureService;

     @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private AdditionalServiceService additionalServiceService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private FishingInstructorService fishingInstructorService;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(value = "/all")
    public ResponseEntity<List<AdventureDTO>> getAllAdventures(){
        List<Adventure> adventures = adventureService.findAll();

        List<AdventureDTO> adventuresDTO = new ArrayList<>();
        for (Adventure a : adventures){
            adventuresDTO.add(mapper.map(a, AdventureDTO.class));
        }

        return new ResponseEntity<>(adventuresDTO, HttpStatus.OK);
    }

    @PostMapping("/addAdventure")
    @PreAuthorize("hasRole('fishingInstructor')")
    public ResponseEntity<AdventureDTO> addAdventure(@RequestBody AdventureCreateDTO adventureDTO,Principal principal) throws Exception {
        FishingInstructor instructor = fishingInstructorService.findByEmail(principal.getName());
        Adventure adventure = new Adventure();
        adventure.setName(adventureDTO.getName());
        adventure.setDescription(adventureDTO.getDescription());
        adventure.setMaxPersonNum(Integer.parseInt(adventureDTO.getMaxPersonNum()));
        adventure.setPrice(adventureDTO.getPrice());
        adventure.setCancelFee(adventureDTO.getCancelFee());
        adventure.setAddress(new Address(adventureDTO.getStreet(),adventureDTO.getCity(),adventureDTO.getPostal_code(),adventureDTO.getCountry()));
        adventure.setFishingInstructor(instructor);
        adventure.setKind(EntityKind.ADVENTURE);

        Set<Rule> rules = ruleService.createRuleFromString(adventureDTO.getRules(),adventure);
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

    @DeleteMapping(value = "/deleteAdventure/{id}")
    @PreAuthorize("hasRole('fishingInstructor')")
    public ResponseEntity<String> deleteAdventure(@PathVariable Integer id,Principal principal){
        Adventure adventure = adventureService.findOne(id);
        if(adventure == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!adventureService.canDeleteAdventure(adventure)){
            return new ResponseEntity<>("Adventure has reservations.Deletion is not possible.",HttpStatus.OK);
        }
        adventureService.deleteAdventure(adventure);
        return new ResponseEntity<>("Deletion is successful.",HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AdventureDTO> getAdventure(@PathVariable Integer id,Principal principal){
        Adventure adventure = adventureService.findOne(id);
        if(adventure == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AdventureDTO dto = mapper.map(adventure,AdventureDTO.class);
        System.out.println(dto);
        dto.setId(adventure.getId());
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }


    @GetMapping(value = "/allByOwner")
    @PreAuthorize("hasRole('fishingInstructor')")
    public ResponseEntity<List<AdventureDTO>> getAllAdventuresByOwnerId(Principal principal){
        List<Adventure> adventures = adventureService.findAllByOwnerEmail(principal.getName());
        List<AdventureDTO> adventureDTO = new ArrayList<>();
        for (Adventure c : adventures){
            adventureDTO.add(mapper.map(c,AdventureDTO.class));
        }
        return new ResponseEntity<>(adventureDTO, HttpStatus.OK);
    }


    @GetMapping(value = "/findMainPhoto/{id}")
    @PreAuthorize("hasRole('fishingInstructor')")
    public ResponseEntity<String> getMainPhoto(@PathVariable Integer id){
        Adventure a = adventureService.findOne(id);
        String path = null;
        for (Image img: a.getImages()){
            if(img.getIsMainPhoto()){
                path = img.getPath();
            }
        }
        return new ResponseEntity<>(path,HttpStatus.OK);
    }

    @PutMapping("/updateAdventure")
    @PreAuthorize("hasRole('fishingInstructor')")
    public ResponseEntity<AdventureDTO> updateAdventure(@RequestBody AdventureDTO adventureDTO,Principal principal) throws IOException {
        Adventure adventure = adventureService.findOne(adventureDTO.getId());

        if(adventure == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        adventure.setName(adventureDTO.getName());
        adventure.setPrice(adventureDTO.getPrice());
        adventure.setMaxPersonNum(adventureDTO.getMaxPersonNum());
        adventure.setDescription(adventureDTO.getDescription());
        Address a = mapper.map(adventureDTO.getAddress(),Address.class);
        adventure.setAddress(a);

        adventureService.save(adventure);
        return new ResponseEntity<>(mapper.map(adventure,AdventureDTO.class),HttpStatus.OK);
    }

    }

