package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.user.CottageOwner;
import ftn.mrs.isa.rentalapp.repository.CottageOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CottageOwnerService {

    @Autowired
    CottageOwnerRepository cottageOwnerRepository;

    public CottageOwner findOne(Integer id){return cottageOwnerRepository.findById(id).orElse(null);}

    public void updateCottageOwner(CottageOwner cottageOwner){
        cottageOwnerRepository.save(cottageOwner);
    }
}
