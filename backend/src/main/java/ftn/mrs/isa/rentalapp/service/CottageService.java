package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.repository.CottageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CottageService {

    @Autowired
    private CottageRepository cottageRepository;

    public List<Cottage> findAll(){return cottageRepository.findAll();}

    public Cottage findOne(Integer id){return cottageRepository.findById(id).orElse(null);}

    public void save(Cottage cottage){cottageRepository.save(cottage);}


}