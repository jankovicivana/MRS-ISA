package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.dto.FishingInstructorDTO;
import ftn.mrs.isa.rentalapp.model.user.Administrator;
import ftn.mrs.isa.rentalapp.model.user.FishingInstructor;
import ftn.mrs.isa.rentalapp.repository.FishingInstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FishingInstructorService {

    @Autowired
    FishingInstructorRepository fishingInstructorRepository;

    public FishingInstructor findOne(Integer id) {
        return fishingInstructorRepository.findById(id).orElse(null);
    }

    public void updateInstructor(FishingInstructor fishingInstructor) {
        fishingInstructorRepository.save(fishingInstructor);
    }

    public FishingInstructor findByEmail(String string) {return fishingInstructorRepository.findByEmail(string);}

}
