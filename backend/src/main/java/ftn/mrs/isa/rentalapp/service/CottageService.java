package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.repository.CottageRepository;
import ftn.mrs.isa.rentalapp.repository.QuickReservationRepository;
import ftn.mrs.isa.rentalapp.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CottageService {

    @Autowired
    private CottageRepository cottageRepository;

    @Autowired
    private ReservationRepository reservationsRepository;

    @Autowired
    private QuickReservationRepository quickReservationRepository;

    public List<Cottage> findAll(){return cottageRepository.findAll();}

    public List<Cottage> findAllByOwnerEmail(String email){return cottageRepository.findAllByOwnerEmail(email);}

    public Cottage findOne(Integer id){return cottageRepository.findById(id).orElse(null);}

    public void save(Cottage cottage){cottageRepository.save(cottage);}

    public boolean canDeleteCottage(Cottage cottage) {
        List<Reservation> l = reservationsRepository.getReservationByEntity(LocalDate.now(), cottage.getId());
        return l.size() == 0;
    }

    public void deleteCottage(Cottage cottage) {
        cottage.setDeleted(true);
        cottageRepository.save(cottage);
        for(Reservation r : cottage.getReservations()){
            r.setDeleted(true);
        }
        reservationsRepository.saveAll(cottage.getReservations());

        for(QuickReservation r : cottage.getQuickReservations()){
            r.setDeleted(true);
        }
        quickReservationRepository.saveAll(cottage.getQuickReservations());
    }

}