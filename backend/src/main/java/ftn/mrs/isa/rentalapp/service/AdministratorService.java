package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.user.Administrator;
import ftn.mrs.isa.rentalapp.model.user.BoatOwner;
import ftn.mrs.isa.rentalapp.repository.AdministratorRepository;
import ftn.mrs.isa.rentalapp.repository.AdventureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    public void save(Administrator admin) {
        administratorRepository.save(admin);
    }

    public Administrator findByEmail(String string) {return administratorRepository.findByEmail(string);}

}
