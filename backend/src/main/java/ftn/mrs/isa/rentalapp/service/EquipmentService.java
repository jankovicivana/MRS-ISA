package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.entity.Adventure;
import ftn.mrs.isa.rentalapp.model.entity.Boat;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import ftn.mrs.isa.rentalapp.model.entity.FishingEquipment;
import ftn.mrs.isa.rentalapp.repository.FishingEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class EquipmentService {

    @Autowired
    private FishingEquipmentRepository fishingEquipmentRepository;

    public Set<FishingEquipment> createFishingEquipmentFromString(Set<String> equipDTO, EntityType entity){
        if (equipDTO == null) {
            return null;
        }
        Set<FishingEquipment> equipmentList = new HashSet<>();
        for (String e: equipDTO) {
            FishingEquipment r = new FishingEquipment();
            r.setEquipment(e);
            if(entity instanceof Adventure)
                r.setAdventure((Adventure) entity);
            else
                r.setBoat((Boat) entity);
            equipmentList.add(r);
        }
        return equipmentList;
    }

    public void addFishingEquipments(Set<FishingEquipment> fishingEquipments){
        fishingEquipmentRepository.saveAll(fishingEquipments);
    }

    public void saveFishingEquipment(FishingEquipment equip) {
        fishingEquipmentRepository.save(equip);
    }

    public FishingEquipment findFishingEquipment(Integer id) {
        return fishingEquipmentRepository.findById(id).orElseGet(null);
    }

    public void removeFishingEquipment(Integer id) {
        fishingEquipmentRepository.deleteById(id);
    }
}