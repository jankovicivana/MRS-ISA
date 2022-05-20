package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.entity.Adventure;
import ftn.mrs.isa.rentalapp.model.entity.Boat;
import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.repository.AdventureRepository;
import ftn.mrs.isa.rentalapp.repository.QuickReservationRepository;
import ftn.mrs.isa.rentalapp.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class AdventureService {

    @Autowired
    private AdventureRepository adventureRepository;

    @Autowired
    private ReservationRepository reservationsRepository;

    @Autowired
    private QuickReservationRepository quickReservationRepository;


    public Adventure findOne(Integer id){return adventureRepository.findById(id).orElse(null);}

    public List<Adventure> findAll(){return adventureRepository.findAll();}

    public List<Adventure> findAllByOwnerEmail(String email){return adventureRepository.findAllByOwnerEmail(email);}

    public void save(Adventure adventure){adventureRepository.save(adventure);}


    public boolean canDeleteAdventure(Adventure adventure) {
        List<Reservation> l = reservationsRepository.getReservationByEntity(LocalDate.now(), adventure.getId());
        return l.size() == 0;
    }

    public void deleteAdventure(Adventure adventure) {
        adventure.setDeleted(true);
        adventureRepository.save(adventure);
        for(Reservation r : adventure.getReservations()){
            r.setDeleted(true);
        }
        reservationsRepository.saveAll(adventure.getReservations());

        for(QuickReservation r : adventure.getQuickReservations()){
            r.setDeleted(true);
        }
        quickReservationRepository.saveAll(adventure.getQuickReservations());
    }


    public void saveAll(Set<Adventure> adventures) {
        adventureRepository.saveAll(adventures);
    }
}
