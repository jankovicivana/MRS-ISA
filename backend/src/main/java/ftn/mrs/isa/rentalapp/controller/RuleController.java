package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.CottageDTO;
import ftn.mrs.isa.rentalapp.dto.RuleDTO;
import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.model.entity.Rule;
import ftn.mrs.isa.rentalapp.service.CottageService;
import ftn.mrs.isa.rentalapp.service.RuleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/rules")
public class RuleController {

    @Autowired
    private CottageService cottageService;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/addRule")
    public ResponseEntity<RuleDTO> addRule(@RequestBody RuleDTO ruleDTO){
        if(ruleDTO.getEntityId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Cottage cottage = cottageService.findOne(ruleDTO.getEntityId());

        if(cottage == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Rule rule = new Rule();
        rule.setRule(ruleDTO.getRule());
        rule.setEntity(cottage);
        cottage.getRules().add(rule);

        ruleService.save(rule);
        return new ResponseEntity<>(mapper.map(rule, RuleDTO.class),HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/deleteRule/{id}")
    public  ResponseEntity<Void> deleteRule(@PathVariable Integer id){
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
