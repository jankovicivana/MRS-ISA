package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.entity.AdditionalService;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import ftn.mrs.isa.rentalapp.repository.AdditionalServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AdditionalServiceService {

    @Autowired
    private AdditionalServiceRepository additionalServiceRepository;

    public void save(AdditionalService additionalService){additionalServiceRepository.save(additionalService);}

    public AdditionalService findOne(Integer id){return  additionalServiceRepository.findById(id).orElse(null);}

    public void remove(Integer id){additionalServiceRepository.deleteById(id);}

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

    public void addAdditionalServices(Set<AdditionalService> additionalServices){
        additionalServiceRepository.saveAll(additionalServices);
    }
}