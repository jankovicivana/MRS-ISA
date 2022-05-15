package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.entity.Adventure;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.model.user.FishingInstructor;
import ftn.mrs.isa.rentalapp.repository.FishingInstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FishingInstructorService {

    @Autowired
    FishingInstructorRepository fishingInstructorRepository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private AdventureService adventureService;


    public FishingInstructor findOne(Integer id) {
        return fishingInstructorRepository.findById(id).orElse(null);
    }

    public void updateInstructor(FishingInstructor fishingInstructor) {
        fishingInstructorRepository.save(fishingInstructor);
    }

    public FishingInstructor findByEmail(String string) {return fishingInstructorRepository.findByEmail(string);}


    public boolean canDeleteInstructor(FishingInstructor instructor) {
        List<Reservation> l = reservationService.getFutureReservationByFishingInstructor(instructor.getId());
        return l.size() == 0;
    }

    public void deleteInstructor(FishingInstructor instructor) {
        instructor.setDeleted(true);
        fishingInstructorRepository.save(instructor);
        List<Reservation> reservations = reservationService.getReservationByFishingInstructor(instructor.getId());
        for(Adventure a : instructor.getAdventures()){
            a.setDeleted(true);
        }
        adventureService.saveAll(instructor.getAdventures());
        for(Reservation r : reservations){
            r.setDeleted(true);
        }
        reservationService.saveAll(reservations);
    }


    public List<FishingInstructor> findAll() {
        return fishingInstructorRepository.findAll();
    }
}
