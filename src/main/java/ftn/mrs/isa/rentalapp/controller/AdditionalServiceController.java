package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.AdditionalServiceDTO;
import ftn.mrs.isa.rentalapp.model.entity.AdditionalService;
import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.model.entity.Rule;
import ftn.mrs.isa.rentalapp.service.AdditionalServiceService;
import ftn.mrs.isa.rentalapp.service.CottageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/additionalServices")
public class AdditionalServiceController {

    @Autowired
    private CottageService cottageService;

    @Autowired
    private AdditionalServiceService additionalServiceService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/addAdditionalService")
    public ResponseEntity<AdditionalServiceDTO> addRule(@RequestBody AdditionalServiceDTO additionalServiceDTO){
        if(additionalServiceDTO.getEntityId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Cottage cottage = cottageService.findOne(additionalServiceDTO.getEntityId());

        if(cottage == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        AdditionalService additionalService = new AdditionalService();
        additionalService.setName(additionalServiceDTO.getName());
        additionalService.setEntity(cottage);
        cottage.getAdditionalServices().add(additionalService);

        additionalServiceService.save(additionalService);
        return new ResponseEntity<>(mapper.map(additionalService, AdditionalServiceDTO.class),HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/deleteAdditionalService/{id}")
    public  ResponseEntity<Void> deleteAdditionalService(@PathVariable Integer id){
        AdditionalService additionalService = additionalServiceService.findOne(id);

        if(additionalService != null){
            additionalServiceService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
