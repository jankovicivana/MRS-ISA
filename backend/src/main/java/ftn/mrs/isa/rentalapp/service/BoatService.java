package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.entity.Boat;
import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.repository.BoatRepository;
import ftn.mrs.isa.rentalapp.repository.QuickReservationRepository;
import ftn.mrs.isa.rentalapp.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BoatService {

    @Autowired
    private BoatRepository boatRepository;

    @Autowired
    private ReservationRepository reservationsRepository;

    @Autowired
    private QuickReservationRepository quickReservationRepository;

    public void save(Boat boat){boatRepository.save(boat);}

    public List<Boat> findAll(){return boatRepository.findAll();}

    public Boat findOne(Integer id){return boatRepository.findById(id).orElse(null);}

    public boolean canDeleteBoat(Boat boat){
        List<Reservation> l = reservationsRepository.getReservationByEntity(LocalDate.now(), boat.getId());
        return l.size()==0;
    }

    public void deleteBoat(Boat boat){
        boat.setDeleted(true);
        boatRepository.save(boat);
        for(Reservation r : boat.getReservations()){
            r.setDeleted(true);
        }
        reservationsRepository.saveAll(boat.getReservations());

        for(QuickReservation r : boat.getQuickReservations()){
            r.setDeleted(true);
        }
        quickReservationRepository.saveAll(boat.getQuickReservations());
    }
}
