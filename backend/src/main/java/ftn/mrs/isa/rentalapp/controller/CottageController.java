package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.CottageCreateDTO;
import ftn.mrs.isa.rentalapp.dto.CottageDTO;
import ftn.mrs.isa.rentalapp.dto.EntitySearchDTO;
import ftn.mrs.isa.rentalapp.dto.EntityTypeDTO;
import ftn.mrs.isa.rentalapp.model.entity.*;
import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.model.user.CottageOwner;
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
import java.time.LocalDateTime;
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
    private ModelMapper mapper;


    @GetMapping(value = "/all")
    public ResponseEntity<List<CottageDTO>> getAllCottages(){
        List<CottageDTO> cottagesDTO = cottageService.getAllCottages();
        return new ResponseEntity<>(cottagesDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/allByOwner")
    @PreAuthorize("hasRole('cottageOwner')")
    public ResponseEntity<List<CottageDTO>> getAllCottagesByOwnerId(Principal principal){
        List<CottageDTO> cottagesDTO = cottageService.getAllCottagesByOwner(principal.getName());
        return new ResponseEntity<>(cottagesDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CottageDTO> getCottage(@PathVariable Integer id){
        Cottage cottage = cottageService.findOne(id);
        if(cottage == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(mapper.map(cottage,CottageDTO.class),HttpStatus.OK);
    }

    @PostMapping("/addCottage")
    @PreAuthorize("hasRole('cottageOwner')")
    public ResponseEntity<CottageDTO> addCottage(@RequestBody CottageCreateDTO cottageCreateDTO,Principal principal) throws IOException {
        Cottage cottage = cottageService.createCottage(cottageCreateDTO,principal.getName());
        return new ResponseEntity<>(mapper.map(cottage,CottageDTO.class),HttpStatus.CREATED);
    }

    @PutMapping("/updateCottage")
    @PreAuthorize("hasRole('cottageOwner')")
    public ResponseEntity<CottageDTO> updateCottage(@RequestBody CottageDTO cottageDTO) throws IOException {
        Cottage cottage = cottageService.updateCottage(cottageDTO);
        if(cottage == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(mapper.map(cottage,CottageDTO.class),HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteCottage/{id}")
    @PreAuthorize("hasAnyRole('admin','cottageOwner')")
    public ResponseEntity<String> deleteCottage(@PathVariable Integer id,Principal principal){
        return cottageService.deleteCottage(id);
    }

    @GetMapping(value = "/getAverageGrade")
    @PreAuthorize("hasRole('cottageOwner')")
    public ResponseEntity<Double> getCottageAverageGrade(Principal principal){
        Double averageGrade = cottageService.getCottagesAverageGrade(principal.getName());
        return new ResponseEntity<>(averageGrade, HttpStatus.OK);
    }

    @PostMapping(value = "/getAvailable")
    @PreAuthorize("hasRole('cottageOwner')")
    public ResponseEntity<List<CottageDTO>> getAvailable(@RequestBody EntitySearchDTO params,Principal principal){
        List<CottageDTO> entitiesDTO = cottageService.getAvailableCottages(params,principal.getName());
        return new ResponseEntity<>(entitiesDTO, HttpStatus.OK);
    }

}
