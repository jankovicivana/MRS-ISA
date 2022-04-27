package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.entity.Adventure;
import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import ftn.mrs.isa.rentalapp.repository.QuickReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuickReservationService {

    @Autowired
    private QuickReservationRepository quickReservationRepository;

    public void save(QuickReservation quickReservation){quickReservationRepository.save(quickReservation);}

}
