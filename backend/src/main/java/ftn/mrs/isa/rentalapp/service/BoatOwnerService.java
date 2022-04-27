package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.user.BoatOwner;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.repository.BoatOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoatOwnerService {

    @Autowired
    BoatOwnerRepository boatOwnerRepository;

    public BoatOwner findByEmail(String string) {return boatOwnerRepository.findByEmail(string);}

}
