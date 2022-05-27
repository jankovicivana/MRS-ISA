package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.entity.Boat;
import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.model.user.BoatOwner;
import ftn.mrs.isa.rentalapp.model.user.CottageOwner;
import ftn.mrs.isa.rentalapp.repository.CottageOwnerRepository;
import ftn.mrs.isa.rentalapp.repository.CottageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CottageOwnerService {

    @Autowired
    CottageOwnerRepository cottageOwnerRepository;

    @Autowired
    CottageRepository cottageRepository;

    @Autowired
    ReservationService reservationService;

    public CottageOwner findOne(Integer id){return cottageOwnerRepository.findById(id).orElse(null);}

    public CottageOwner findByEmail(String email){return  cottageOwnerRepository.findByEmail(email);}

    public List<CottageOwner> findAll() {
        return cottageOwnerRepository.findAll();
    }

    public boolean canDeleteCottageOwner(CottageOwner owner) {
        List<Reservation> l = reservationService.getFutureReservationByCottageOwner(owner.getId());
        return l.size() == 0;
    }

    public void deleteCottageOwner(CottageOwner owner) {
        owner.setDeleted(true);
        cottageOwnerRepository.save(owner);
        List<Reservation> reservations = reservationService.getReservationByBoatOwner(owner.getId());
        for(Cottage a : owner.getCottages()) {
            a.setDeleted(true);
        }
        cottageRepository.saveAll(owner.getCottages());
        for(Reservation r : reservations){
            r.setDeleted(true);
        }
        reservationService.saveAll(reservations);
    }



    public void updateCottageOwner(CottageOwner cottageOwner){
        cottageOwnerRepository.save(cottageOwner);
    }
}
