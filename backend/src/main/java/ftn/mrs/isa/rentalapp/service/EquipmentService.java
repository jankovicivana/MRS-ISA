package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.entity.Adventure;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import ftn.mrs.isa.rentalapp.model.entity.FishingEquipment;
import ftn.mrs.isa.rentalapp.model.entity.Rule;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class EquipmentService {

    public Set<FishingEquipment> createFishingEquipmentFromString(Set<String> equipDTO, Adventure adventure){
        if (equipDTO == null) {
            return null;
        }
        Set<FishingEquipment> equipmentList = new HashSet<>();
        for (String e: equipDTO) {
            FishingEquipment r = new FishingEquipment();
            r.setEquipment(e);
            r.setAdventure(adventure);
            equipmentList.add(r);
        }
        return equipmentList;
    }
}