package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.*;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import ftn.mrs.isa.rentalapp.dto.CottageDTO;
import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.service.CottageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



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
            cottagesDTO.add(mapper.map(c,CottageDTO.class));
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

        return new ResponseEntity<>(mapper.map(cottage,CottageDTO.class),HttpStatus.OK);
    }

    @PostMapping("/addCottage")
    public ResponseEntity<CottageDTO> addCottage(@RequestBody CottageCreateDTO cottageCreateDTO) throws IOException {
        Cottage cottage = new Cottage();
        cottage.setName(cottageCreateDTO.getName());
        cottage.setDescription(cottageCreateDTO.getDescription());
        cottage.setMaxNumPerson(cottageCreateDTO.getMaxNumPerson());
        cottage.setPrice(cottageCreateDTO.getPrice());
        cottage.setAddress(new Address(cottageCreateDTO.getStreet(),cottageCreateDTO.getCity(),Integer.parseInt(cottageCreateDTO.getPostalCode()),cottageCreateDTO.getCountry()));
        //dodati i za cottageOwnera

        Set<Room> rooms =  roomService.createRoomFromString(cottageCreateDTO.getRooms(),cottage);
        cottage.setRooms(rooms);

        Set<Rule> rules = ruleService.createRuleFromString(cottageCreateDTO.getRules(),cottage);
        cottage.setRules(rules);

        Set<AdditionalService> services = additionalServiceService.createAddServiceFromString(cottageCreateDTO.getAdditionalServices(),cottage);
        cottage.setAdditionalServices(services);


        Set<Image> images = imageService.createImageFromString(cottageCreateDTO.getImages(),cottage);
        cottage.setImages(images);


        cottageService.save(cottage);

        return new ResponseEntity<>(mapper.map(cottage,CottageDTO.class),HttpStatus.CREATED);
    }

    @PutMapping("/updateCottage")
    public ResponseEntity<CottageDTO> updateCottage(@RequestBody CottageDTO cottageDTO) throws IOException {
        Cottage cottage = cottageService.findOne(cottageDTO.getId());

        if(cottage == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        cottage.setName(cottageDTO.getName());
        cottage.setPrice(cottageDTO.getPrice());
        cottage.setMaxNumPerson(cottageDTO.getMaxNumPerson());
        cottage.setDescription(cottageDTO.getDescription());
        Address a = mapper.map(cottageDTO.getAddress(),Address.class);
        cottage.setAddress(a);

        cottageService.save(cottage);
        return new ResponseEntity<>(mapper.map(cottage,CottageDTO.class),HttpStatus.OK);
    }

}
