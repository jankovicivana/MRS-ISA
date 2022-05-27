package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.dto.EntitySearchDTO;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import ftn.mrs.isa.rentalapp.repository.EntityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntityService {

    @Autowired
    private EntityTypeRepository entityRepository;

    public List<EntityType> getByParams(EntitySearchDTO data){

        List<EntityType> entities;
        Double price = data.getPrice().doubleValue();
        // entities = entityRepository.getByParams(price, data.getCity(), data.getPeople(), data.getRating());
        entities = entityRepository.getByParams();

        System.out.println(entityRepository.findAllByPriceLessThan(price));
        return entities;
    }

    public List<EntityType> getAll(){return entityRepository.findAll();}

    public EntityType findOne(Integer id) {
        return entityRepository.getById(id);
    }
}
