package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.CottageCreateDTO;
import ftn.mrs.isa.rentalapp.dto.CottageDTO;
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

    @GetMapping(value = "/allByOwner")
    @PreAuthorize("hasRole('cottageOwner')")
    public ResponseEntity<List<CottageDTO>> getAllCottagesByOwnerId(Principal principal){
        List<Cottage> cottages = cottageService.findAllByOwnerEmail(principal.getName());
        List<CottageDTO> cottagesDTO = new ArrayList<>();
        for (Cottage c : cottages){
            cottagesDTO.add(mapper.map(c,CottageDTO.class));
        }

        return new ResponseEntity<>(cottagesDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('cottageOwner')")
    public ResponseEntity<CottageDTO> getCottage(@PathVariable Integer id,Principal principal){
        System.out.println(id);
        Cottage cottage = cottageService.findOne(id);
        if(cottage == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(mapper.map(cottage,CottageDTO.class),HttpStatus.OK);
    }

    @PostMapping("/addCottage")
    @PreAuthorize("hasRole('cottageOwner')")
    public ResponseEntity<CottageDTO> addCottage(@RequestBody CottageCreateDTO cottageCreateDTO) throws IOException {
        Cottage cottage = new Cottage();
        cottage.setName(cottageCreateDTO.getName());
        cottage.setDescription(cottageCreateDTO.getDescription());
        cottage.setMaxNumPerson(cottageCreateDTO.getMaxNumPerson());
        cottage.setPrice(cottageCreateDTO.getPrice());
        cottage.setAddress(new Address(cottageCreateDTO.getStreet(),cottageCreateDTO.getCity(),Integer.parseInt(cottageCreateDTO.getPostalCode()),cottageCreateDTO.getCountry()));
        cottage.setKind(EntityKind.COTTAGE);
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
    @PreAuthorize("hasRole('cottageOwner')")
    public ResponseEntity<CottageDTO> updateCottage(@RequestBody CottageDTO cottageDTO) throws IOException {
        Cottage cottage = cottageService.findOne(cottageDTO.getId());

        if(cottage == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //Cottage updatedCottage = mapper.map(cottageDTO,Cottage.class);

        cottage.setName(cottageDTO.getName());
        cottage.setPrice(cottageDTO.getPrice());
        cottage.setMaxNumPerson(cottageDTO.getMaxNumPerson());
        cottage.setDescription(cottageDTO.getDescription());
        Address a = mapper.map(cottageDTO.getAddress(),Address.class);
        cottage.setAddress(a);

        cottageService.save(cottage);
        return new ResponseEntity<>(mapper.map(cottage,CottageDTO.class),HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteCottage/{id}")
    @PreAuthorize("hasAnyRole('admin','cottageOwner')")
    public ResponseEntity<String> deleteCottage(@PathVariable Integer id,Principal principal){
        Cottage cottage = cottageService.findOne(id);
        if(cottage == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!cottageService.canDeleteCottage(cottage)){
            return new ResponseEntity<>("Adventure has reservations.Deletion is not possible.",HttpStatus.BAD_REQUEST);
        }
        cottageService.deleteCottage(cottage);
        return new ResponseEntity<>("Deletion is successful.",HttpStatus.OK);
    }

    @GetMapping(value = "/getAverageGrade")
    @PreAuthorize("hasRole('cottageOwner')")
    public ResponseEntity<Double> getCottageAverageGrade(Principal principal){
        List<Cottage> cottages = cottageService.findAllByOwnerEmail(principal.getName());
        double averageGrade = 0.0;
        for (Cottage c : cottages){
            averageGrade += c.getAverageGrade();
        }
        averageGrade = averageGrade / cottages.size();
        return new ResponseEntity<>(averageGrade, HttpStatus.OK);
    }

}
