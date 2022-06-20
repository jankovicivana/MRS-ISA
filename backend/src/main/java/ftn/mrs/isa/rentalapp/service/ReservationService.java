package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.dto.*;
import ftn.mrs.isa.rentalapp.model.entity.*;
import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.model.system_info.RankingInfo;
import ftn.mrs.isa.rentalapp.model.user.Client;
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
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AdventureService adventureService;

    @Autowired
    private CottageService cottageService;

    @Autowired
    private BoatService boatService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SystemInfoService systemInfoService;

    @Autowired
    private QuickReservationService quickReservationService;

    @Autowired
    private CottageRepository cottageRepository;

    @Autowired
    private BoatRepository boatRepository;

    @Autowired
    private AdventureRepository adventureRepository;

    @Autowired
    private RankingInfoService rankingInfoService;



    public void save(Reservation reservation){reservationRepository.save(reservation);}

    public List<Reservation> findAllHistoryByUser(Integer id) {return reservationRepository.findAllHistoryByUser(LocalDateTime.now(),id); }

    public List<Reservation> getFutureReservationByFishingInstructor(Integer id) {
        return reservationRepository.getFutureReservationByFishingInstructor(LocalDateTime.now(),id);
    }
     public List<Reservation> getHistoryReservationByInstructor(Integer id) {
        return reservationRepository.getHistoryReservationByFishingInstructor(LocalDateTime.now(),id);
        }

    public List<Reservation> getReservationByFishingInstructor(Integer id) {
        return reservationRepository.getReservationByFishingInstructor(id);
    }

    public void saveAll(List<Reservation> reservations) {
        reservationRepository.saveAll(reservations);
    }

    public List<Reservation> getFutureReservationByBoatOwner(Integer id) {
        return reservationRepository.getFutureReservationByBoatOwner(LocalDateTime.now(),id);
    }

    public List<Reservation> getHistoryReservationByBoatOwner(Integer id) {
        return reservationRepository.getHistoryReservationByBoatOwner(LocalDateTime.now(),id);
    }

    public List<Reservation> getCurrentReservationByBoatOwner(Integer id) {
        return reservationRepository.getCurrentReservationByBoatOwner(LocalDateTime.now(),id);
    }

    public List<Reservation> getFutureReservationByCottageOwner(Integer id) {
        return reservationRepository.getFutureReservationByCottageOwner(LocalDateTime.now(),id);
    }

    public List<Reservation> getReservationByBoatOwner(Integer id) {
        return reservationRepository.getReservationByBoatOwner(id);
    }
    public List<Reservation> findAllHistoryByCottageOwner(Integer id) {return reservationRepository.findAllHistoryByCottageOwner(LocalDateTime.now(),id); }

    public List<Reservation> findAllUpcomingByCottageOwner(Integer id) {return reservationRepository.findAllUpcomingByCottageOwner(LocalDateTime.now(),id); }

    public List<Reservation> findAllCurrentByCottageOwner(Integer id) {return reservationRepository.findAllCurrentByCottageOwner(LocalDateTime.now(),id); }

    public List<Reservation> findAllByEntity(Integer id) {return reservationRepository.findAllByEntity(id); }

    public boolean isReserved(Integer id, LocalDateTime start, LocalDateTime end){
        List<Reservation> reservations = reservationRepository.getReserved(id, start, end);
        return !reservations.isEmpty();
    }

    public boolean isAvailableInstructor(Integer id, LocalDateTime start, LocalDateTime end){
        List<Reservation> reservations = reservationRepository.getReservedByInstructor(id, start, end);
        System.out.println("Reservations: " + reservations);
        return !reservations.isEmpty();
    }

    public List<Reservation> findAllByUser(Integer id) {
        return  reservationRepository.findAllByUser(id);
    }

    public List<Reservation> getHistoryByClient(Integer id) {
        return reservationRepository.findAllHistoryByClient(LocalDateTime.now(),id);
    }

    public List<Reservation> getUpcomingByClient(Integer id) {
        return reservationRepository.findAllUpcomingByClient(LocalDateTime.now(),id);
    }

    public Reservation getById(Integer resId) {
        return reservationRepository.findById(resId).orElse(null);
    }


    public void cancelReservation(Integer id) {
        reservationRepository.cancel(id);
    }


    public List<Reservation> getHistoryReservation() {
        return reservationRepository.getReservationsInHistory(LocalDateTime.now());
    }

    public List<Reservation> getCurrentReservationByInstructor(Integer id) {
        return reservationRepository.findAllCurrentByInstructor(LocalDateTime.now(),id);
    }

    public List<Reservation> getFutureReservationByInstructor(Integer id) {
        return reservationRepository.findAllUpcomingByInstructor(LocalDateTime.now(),id);
    }

    public boolean isCanceled(Client client, LocalDateTime start, LocalDateTime end, EntityType entity) {
        List<Reservation> r = reservationRepository.findCanceledByClient(client.getId(), start, end, entity.getId());
        return !r.isEmpty();
    }

    public ResponseEntity<ReservationDTO> get(Integer id) {
        Reservation r = reservationRepository.findById(id).orElse(null);
        ReservationDTO rdto = mapper.map(r, ReservationDTO.class);
        String entityType = EntityKind.toString(r.getEntity().getKind());
        rdto.getEntity().setType(entityType);
        if(entityType.equals("Cottage")){
            Cottage c = cottageService.findOne(r.getEntity().getId());
            rdto.setCottage(mapper.map(c, CottageDTO.class));
        } else  if(entityType.equals("Boat")){
            Boat b = boatService.findOne(r.getEntity().getId());
            rdto.setBoat(mapper.map(b, BoatDTO.class));
        } else{
            Adventure a = adventureService.findOne(r.getEntity().getId());
            rdto.setAdventure(mapper.map(a, AdventureDTO.class));
        }
        return new ResponseEntity<>(rdto, HttpStatus.OK);
    }

    public ResponseEntity<String> makeReservationFromQuick(Integer id, String username) throws MessagingException {
        Client c = clientService.findByEmail(username);
        QuickReservation quickReservation = quickReservationService.findOne(id);
        quickReservation.setIsReserved(true);
        Reservation r = new Reservation();
        r.setQuickReservation(quickReservation);
        r.setEntity(quickReservation.getEntity());
        r.setClient(c);
        r.setPrice(quickReservation.getDiscountedPrice());
        r.setStartDateTime(quickReservation.getStartDateTime());
        r.setEndDateTime(quickReservation.getEndDateTime());
        r.setIsCanceled(false);
        r.setPersonNum(quickReservation.getMaxPersonNum());
        double systemProfit = systemInfoService.calculateSystemProfit(quickReservation.getEntity().getId(),quickReservation.getDiscountedPrice(),c);
        double advertiserProfit = quickReservation.getDiscountedPrice() - systemProfit;
        r.setAdvertiserProfit(advertiserProfit);
        r.setSystemProfit(systemProfit);
        save(r);
        quickReservationService.save(quickReservation);
        emailService.sendReservationEmail(c.getName(), c.getEmail(), quickReservation.getEntity().getName(), quickReservation.getStartDateTime(), quickReservation.getEndDateTime());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<String> reserve(ReserveDataDTO r, String username) throws MessagingException {
        Client client = clientService.findByEmail(username);
        if(client == null){
            client = clientService.findOne(r.getClientId());
        }
        LocalDateTime start = LocalDateTime.of(r.getStartDate(), r.getStartTime());
        LocalDateTime end = LocalDateTime.of(r.getEndDate(), r.getEndTime());
        try{
            EntityType entity = null;
            if(r.getType().equals("Cottage")){
                entity = cottageRepository.findOneLocked(r.getEntityId());
            } else if (r.getType().equals("Adventure")){
                entity = boatRepository.findOneLocked(r.getEntityId());
            } else{
                entity = adventureRepository.findOneLocked(r.getEntityId());
            }
            if(entity == null || isReserved(r.getEntityId(), start, end)){
                return new ResponseEntity<>("Already reserved.",HttpStatus.NOT_FOUND);  // mozda neka druga greska
            }
            boolean isCanceled = isCanceled(client, start, end, entity);
            if(isCanceled){
                return new ResponseEntity<>("Already canceled.",HttpStatus.BAD_REQUEST);
            }

            List<Reservation> upcoming = getUpcomingByClient(client.getId()); // provjeravamo ima li klijent preklapajuce rezervacije
            for(Reservation u: upcoming){
                if((u.getStartDateTime().isAfter(start) && u.getStartDateTime().isBefore(end)) || (u.getEndDateTime().isAfter(start) && u.getEndDateTime().isBefore(end))){
                    return new ResponseEntity<>("Reservation in the same period.",HttpStatus.NOT_FOUND);
                }
            }

            RankingInfo clientRank = rankingInfoService.findRank(client.getPoints());
            double price = entity.getPrice()-entity.getPrice()*clientRank.getClientDiscount()/100;
            double systemProfit = systemInfoService.calculateSystemProfit(r.getEntityId(),price,client);
            double advertiserProfit = price - systemProfit;
            Reservation res = new Reservation(start, end, entity, price, systemProfit, advertiserProfit, r.getPersonNum(), client, null);
            save(res);

            emailService.sendReservationEmail(client.getName(), client.getEmail(), entity.getName(), start, end);

            return new ResponseEntity<>("Reserved successfully.",HttpStatus.OK);
        }catch(PessimisticLockingFailureException e){
            return new ResponseEntity<>("Already reserved.",HttpStatus.NOT_FOUND);  // mozda neka druga greska
        }

    }
}
