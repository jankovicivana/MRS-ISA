package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.dto.EntitySearchDTO;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import ftn.mrs.isa.rentalapp.repository.EntityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntityService {

    @Autowired
    private EntityTypeRepository entityRepository;


    public List<EntityType> getAll(){return entityRepository.findAll();}

    public EntityType findOne(Integer id) {
        return entityRepository.findById(id).orElse(null);
    }

    public void save(EntityType entity) {
        entityRepository.save(entity);
    }
}
