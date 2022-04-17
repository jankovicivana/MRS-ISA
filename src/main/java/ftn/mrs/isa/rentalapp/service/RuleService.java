package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.model.entity.Rule;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RuleService {

    public Set<Rule> createRuleFromString(Set<String> rulesDTO, Cottage cottage){
        if (rulesDTO == null) {
            return null;
        }
        Set<Rule> rules = new HashSet<>();
        for (String ruleDTO: rulesDTO) {
            Rule r = new Rule();
            r.setRule(ruleDTO);
            r.setEntity(cottage);
            rules.add(r);
        }
        return rules;
    }
}
