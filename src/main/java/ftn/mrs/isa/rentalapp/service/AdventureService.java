package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.entity.Adventure;
import ftn.mrs.isa.rentalapp.repository.AdventureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdventureService {

    @Autowired
    private AdventureRepository adventureRepository;

    public Adventure findOne(Integer id){return adventureRepository.findById(id).orElse(null);}
}
