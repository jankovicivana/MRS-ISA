package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.entity.Boat;
import ftn.mrs.isa.rentalapp.model.entity.NavigationEquipment;
import ftn.mrs.isa.rentalapp.model.entity.NavigationEquipmentType;
import ftn.mrs.isa.rentalapp.repository.NavigationEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class NavigationEquipmentService {
    @Autowired
    private NavigationEquipmentRepository navigationEquipmentRepository;

    public Set<NavigationEquipment> createNavigationEquipmentFromString(Set<String> equipDTO, Boat boat){
        if (equipDTO == null) {
            return null;
        }
        Set<NavigationEquipment> equipmentList = new HashSet<>();
        for (String e: equipDTO) {
            NavigationEquipment r = new NavigationEquipment();
            r.setEquipment(NavigationEquipmentType.getTypeFromString(e));
            r.setBoat(boat);
            equipmentList.add(r);
        }
        return equipmentList;
    }

    public void addNavigationEquipments(Set<NavigationEquipment> navigationEquipments){
        navigationEquipmentRepository.saveAll(navigationEquipments);
    }

    public void saveNavigationEquipment(NavigationEquipment equip) {
        navigationEquipmentRepository.save(equip);
    }

    public NavigationEquipment findNavigationEquipment(Integer id) {
        return navigationEquipmentRepository.findById(id).orElseGet(null);
    }

    public void removeNavigationEquipment(Integer id) {
        navigationEquipmentRepository.deleteById(id);
    }

}
