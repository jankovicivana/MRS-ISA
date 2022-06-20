package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.dto.EntityTypeDTO;
import ftn.mrs.isa.rentalapp.dto.QuickReservationDTO;
import ftn.mrs.isa.rentalapp.model.entity.Adventure;
import ftn.mrs.isa.rentalapp.model.entity.EntityKind;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import ftn.mrs.isa.rentalapp.model.entity.Subscription;
import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import ftn.mrs.isa.rentalapp.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class QuickReservationService {

    @Autowired
    private QuickReservationRepository quickReservationRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AdventureService adventureService;

    @Autowired
    private CottageService cottageService;

    @Autowired
    private CottageRepository cottageRepository;

    @Autowired
    private BoatRepository boatRepository;

    @Autowired
    private AdventureRepository adventureRepository;

    @Autowired
    private AvailablePeriodService availablePeriodService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BoatService boatService;

    public QuickReservation findOne(Integer id){return quickReservationRepository.findById(id).orElse(null);}

    public void save(QuickReservation quickReservation){quickReservationRepository.save(quickReservation);}

    public List<QuickReservation> findQuickReservationByInstructor(Integer id) {return quickReservationRepository.findQuickReservationByInstructor(id); }

    public List<QuickReservation> findQuickReservationByEntity(Integer id) {
        return quickReservationRepository.findQuickReservationByEntity(id);
    }

    public ResponseEntity<String> createQuickReservation(QuickReservationDTO quickReservationDTO) throws MessagingException {

        LocalDateTime start = quickReservationDTO.getStartDateTime();
        LocalDateTime end = quickReservationDTO.getEndDateTime();
        try{
            EntityType entity = adventureRepository.findOneLocked(quickReservationDTO.getEntId());
            if (entity == null){
                entity = cottageRepository.findOneLocked(quickReservationDTO.getEntId());
            }
            if (entity == null){
                entity = boatRepository.findOneLocked(quickReservationDTO.getEntId());
            }

            boolean isAvailable;
            if(entity instanceof Adventure){
                isAvailable = availablePeriodService.isAvailableInstructor(((Adventure) entity).getFishingInstructor().getId(), start,end);
            }else{
                isAvailable = availablePeriodService.isAvailable(entity.getId(), start, end);
            }

            if(!isAvailable || !reservationRepository.getReserved(entity.getId(), start, end).isEmpty()){
                return new ResponseEntity<>("Entity is not available", HttpStatus.BAD_REQUEST);
            }

            List<QuickReservation> quickReservations = findQuickReservationByEntity(entity.getId());
            for (QuickReservation q:quickReservations) {
                if((q.getStartDateTime().isAfter(start) && q.getStartDateTime().isBefore(end)) || (q.getEndDateTime().isAfter(start) && q.getEndDateTime().isBefore(end))){
                    return new ResponseEntity<>("Quick reservation in the same period.",HttpStatus.BAD_REQUEST);
                }
            }

            QuickReservation quickReservation = mapper.map(quickReservationDTO, QuickReservation.class);
            quickReservation.setIsReserved(false);
            quickReservation.setEntity(entity);
            quickReservation.setIsReserved(false);
            quickReservation.setReservation(null);
            System.out.print(quickReservation.getExpirationDateTime());
            quickReservationRepository.save(quickReservation);

            for(Subscription s: entity.getSubscriptions()){
                emailService.sendQuickReservationNotification(s.getClient().getEmail(), s.getClient().getName(), entity.getName());
            }
            return new ResponseEntity<>("Quick reservation successfully made.", HttpStatus.CREATED);
        }catch (PessimisticLockingFailureException e){
            return new ResponseEntity<>("Period is already reserved.",HttpStatus.CONFLICT);
        }

    }
}
