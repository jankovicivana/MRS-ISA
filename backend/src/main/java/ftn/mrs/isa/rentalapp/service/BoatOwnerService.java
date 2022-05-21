package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.entity.Boat;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.model.user.BoatOwner;
import ftn.mrs.isa.rentalapp.model.user.CottageOwner;
import ftn.mrs.isa.rentalapp.repository.BoatOwnerRepository;
import ftn.mrs.isa.rentalapp.repository.BoatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoatOwnerService {

    @Autowired
    BoatOwnerRepository boatOwnerRepository;

    @Autowired
    BoatRepository boatRepository;

    @Autowired
    ReservationService reservationService;

    public BoatOwner findByEmail(String string) {return boatOwnerRepository.findByEmail(string);}

    public List<BoatOwner> findAll() {
        return boatOwnerRepository.findAll();
    }

    public void updateBoatOwner(BoatOwner boatOwner){
        boatOwnerRepository.save(boatOwner);
    }



    public boolean canDeleteBoatOwner(BoatOwner owner) {
        List<Reservation> l = reservationService.getFutureReservationByBoatOwner(owner.getId());
        return l.size() == 0;
    }

    public void deleteBoatOwner(BoatOwner owner) {
        owner.setDeleted(true);
        boatOwnerRepository.save(owner);
        List<Reservation> reservations = reservationService.getReservationByBoatOwner(owner.getId());
        for(Boat a : owner.getBoats()) {
            a.setDeleted(true);
        }
        boatRepository.saveAll(owner.getBoats());
        for(Reservation r : reservations){
            r.setDeleted(true);
        }
        reservationService.saveAll(reservations);
    }


    public BoatOwner findOne(Integer id) {
        return boatOwnerRepository.findById(id).orElse(null);
    }
}
