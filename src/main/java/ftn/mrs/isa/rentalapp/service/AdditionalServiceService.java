package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.entity.AdditionalService;
import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import ftn.mrs.isa.rentalapp.model.entity.Room;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AdditionalServiceService {

    public Set<AdditionalService> createAddServiceFromString(Set<String> addServicesDTO, EntityType entityType){
        if (addServicesDTO == null) {
            return null;
        }
        Set<AdditionalService> additionalServices = new HashSet<>();
        for (String addDTO: addServicesDTO) {
            AdditionalService add = new AdditionalService();
            add.setName(addDTO);
            add.setEntity(entityType);
            additionalServices.add(add);
        }
        return additionalServices;
    }
}