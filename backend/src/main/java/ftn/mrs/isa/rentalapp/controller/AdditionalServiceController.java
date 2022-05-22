package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.AdditionalServiceDTO;
import ftn.mrs.isa.rentalapp.model.entity.AdditionalService;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import ftn.mrs.isa.rentalapp.service.AdditionalServiceService;
import ftn.mrs.isa.rentalapp.service.AdventureService;
import ftn.mrs.isa.rentalapp.service.BoatService;
import ftn.mrs.isa.rentalapp.service.CottageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/additionalServices")
public class AdditionalServiceController {

    @Autowired
    private CottageService cottageService;

    @Autowired
    private AdventureService adventureService;

    @Autowired
    private BoatService boatService;

    @Autowired
    private AdditionalServiceService additionalServiceService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/addAdditionalService")
    @PreAuthorize("hasRole('fishingInstructor')")
    public ResponseEntity<AdditionalServiceDTO> addRule(@RequestBody AdditionalServiceDTO additionalServiceDTO, Principal principal){
        if ( additionalServiceDTO.getEntityId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        EntityType entity = cottageService.findOne(additionalServiceDTO.getEntityId());
        if(entity == null){
            entity = adventureService.findOne(additionalServiceDTO.getEntityId());
        }
        if(entity == null){
            entity = boatService.findOne(additionalServiceDTO.getEntityId());
        }
        if(entity == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AdditionalService additionalService = new AdditionalService();
        additionalService.setName(additionalServiceDTO.getName());
        additionalService.setEntity(entity);
        entity.getAdditionalServices().add(additionalService);

        additionalServiceService.save(additionalService);
        return new ResponseEntity<>(mapper.map(additionalService, AdditionalServiceDTO.class),HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/deleteAdditionalService/{id}")
    @PreAuthorize("hasRole('fishingInstructor')")
    public  ResponseEntity<Void> deleteAdditionalService(@PathVariable Integer id, Principal principal){
        AdditionalService additionalService = additionalServiceService.findOne(id);

        if(additionalService != null){
            additionalServiceService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
