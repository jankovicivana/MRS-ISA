package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import ftn.mrs.isa.rentalapp.model.entity.Rule;
import ftn.mrs.isa.rentalapp.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.HashSet;
import java.util.Set;

@Service
public class RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    public void save(Rule rule){ruleRepository.save(rule);}

    public Rule findOne(Integer id){ return ruleRepository.findById(id).orElseGet(null);}

    public void remove(Integer id){ ruleRepository.deleteById(id);}

    public Set<Rule> createRuleFromString(Set<String> rulesDTO, EntityType entity){
        if (rulesDTO == null) {
            return null;
        }
        Set<Rule> rules = new HashSet<>();
        for (String ruleDTO: rulesDTO) {
            Rule r = new Rule();
            r.setRule(ruleDTO);
            r.setEntity(entity);
            rules.add(r);
        }
        return rules;
    }

    public void addRules(Set<Rule> rules){
        ruleRepository.saveAll(rules);
    }
}