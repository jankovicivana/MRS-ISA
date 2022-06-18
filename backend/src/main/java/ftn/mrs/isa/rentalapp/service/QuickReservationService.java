package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import ftn.mrs.isa.rentalapp.repository.QuickReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuickReservationService {

    @Autowired
    private QuickReservationRepository quickReservationRepository;

    public QuickReservation findOne(Integer id){return quickReservationRepository.findById(id).orElse(null);}

    public void save(QuickReservation quickReservation){quickReservationRepository.save(quickReservation);}

    public List<QuickReservation> findQuickReservationByInstructor(Integer id) {return quickReservationRepository.findQuickReservationByInstructor(id); }

    public List<QuickReservation> findQuickReservationByEntity(Integer id) {
        return quickReservationRepository.findQuickReservationByEntity(id);
    }
}
