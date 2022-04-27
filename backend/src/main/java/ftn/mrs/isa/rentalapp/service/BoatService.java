package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.entity.Boat;
import ftn.mrs.isa.rentalapp.repository.BoatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoatService {

    @Autowired
    private BoatRepository boatRepository;

    public List<Boat> findAll(){return boatRepository.findAll();}

    public Boat findOne(Integer id){return boatRepository.findById(id).orElse(null);}
}
