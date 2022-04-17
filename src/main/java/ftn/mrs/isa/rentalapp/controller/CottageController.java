package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.AddressDTO;
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
@RequestMapping("api/cottages")
public class CottageController {

    @Autowired
    private CottageService cottageService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private AdditionalServiceService additionalServiceService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ModelMapper mapper;



    @GetMapping(value = "/all")
    public ResponseEntity<List<CottageDTO>> getAllCottages(){
        List<Cottage> cottages = cottageService.findAll();

        List<CottageDTO> cottagesDTO = new ArrayList<>();
        for (Cottage c : cottages){
            cottagesDTO.add(new CottageDTO(c));
        }

        return new ResponseEntity<>(cottagesDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CottageDTO> getCottage(@PathVariable Integer id){
        System.out.println(id);
        Cottage cottage = cottageService.findOne(id);
        if(cottage == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new CottageDTO(cottage),HttpStatus.OK);
    }

    @PostMapping("/addCottage")
    public ResponseEntity<CottageDTO> addCottage(@RequestBody CottageDTO cottageDTO) throws IOException {
        Cottage cottage = mapper.map(cottageDTO,Cottage.class);
        cottage.setAddress(new Address(cottageDTO.getStreet(),cottageDTO.getCity(),Integer.parseInt(cottageDTO.getPostal_code()),cottageDTO.getCountry()));
        //dodati i za cottageOwnera

        Set<Room> rooms =  roomService.createRoomFromString(cottageDTO.getRooms(),cottage);
        cottage.setRooms(rooms);

        Set<Rule> rules = ruleService.createRuleFromString(cottageDTO.getRules(),cottage);
        cottage.setRules(rules);

        Set<AdditionalService> services = additionalServiceService.createAddServiceFromString(cottageDTO.getAdditionalServices(),cottage);
        cottage.setAdditionalServices(services);



        Set<Image> images = imageService.createImageFromString(cottageDTO.getImages(),cottage);
        cottage.setImages(images);

        cottageService.save(cottage);
        return new ResponseEntity<>(mapper.map(cottage,CottageDTO.class),HttpStatus.CREATED);
    }

}