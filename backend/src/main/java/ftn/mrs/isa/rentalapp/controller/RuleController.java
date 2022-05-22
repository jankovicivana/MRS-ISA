package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.RuleDTO;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import ftn.mrs.isa.rentalapp.model.entity.Rule;
import ftn.mrs.isa.rentalapp.service.AdventureService;
import ftn.mrs.isa.rentalapp.service.BoatService;
import ftn.mrs.isa.rentalapp.service.CottageService;
import ftn.mrs.isa.rentalapp.service.RuleService;
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
@RequestMapping("api/rules")
public class RuleController {

    @Autowired
    private CottageService cottageService;

    @Autowired
    private AdventureService adventureService;

    @Autowired
    private BoatService boatService;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/addRule")
    @PreAuthorize("hasAnyRole('fishingInstructor','cottageOwner','boatOwner')")
    public ResponseEntity<RuleDTO> addRule(@RequestBody RuleDTO ruleDTO, Principal principal){
        if(ruleDTO.getEntityId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        EntityType entity = cottageService.findOne(ruleDTO.getEntityId());
        if(entity == null) {
            entity = adventureService.findOne(ruleDTO.getEntityId());
        }
        if(entity == null) {
            entity = boatService.findOne(ruleDTO.getEntityId());
        }
        if(entity == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Rule rule = new Rule();
        rule.setRule(ruleDTO.getRule());
        rule.setEntity(entity);
        entity.getRules().add(rule);

        ruleService.save(rule);
        return new ResponseEntity<>(mapper.map(rule, RuleDTO.class),HttpStatus.CREATED);
    }


    @DeleteMapping(value = "/deleteRule/{id}")
    @PreAuthorize("hasAnyRole('fishingInstructor','cottageOwner','boatOwner')")
    public  ResponseEntity<Void> deleteRule(@PathVariable Integer id,Principal principal){
        System.out.print("id jee"+id);
        Rule rule = ruleService.findOne(id);
        System.out.print("kao naslo");

        if(rule != null){
            System.out.print("uslo pred brisanjee");
            ruleService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
