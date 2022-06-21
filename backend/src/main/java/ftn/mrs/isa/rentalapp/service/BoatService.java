package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.dto.BoatCreateDTO;
import ftn.mrs.isa.rentalapp.dto.BoatDTO;
import ftn.mrs.isa.rentalapp.dto.EntitySearchDTO;
import ftn.mrs.isa.rentalapp.model.entity.*;
import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.model.user.BoatOwner;
import ftn.mrs.isa.rentalapp.repository.BoatOwnerRepository;
import ftn.mrs.isa.rentalapp.repository.BoatRepository;
import ftn.mrs.isa.rentalapp.repository.QuickReservationRepository;
import ftn.mrs.isa.rentalapp.repository.ReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class BoatService {

    @Autowired
    private BoatRepository boatRepository;

    @Autowired
    private BoatOwnerRepository boatOwnerRepository;

    @Autowired
    private ReservationRepository reservationsRepository;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private NavigationEquipmentService navigationEquipmentService;

    @Autowired
    private AdditionalServiceService additionalServiceService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private AvailablePeriodService availablePeriodService;


    @Autowired
    private RuleService ruleService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private QuickReservationRepository quickReservationRepository;

    public void save(Boat boat){boatRepository.save(boat);}

    public List<Boat> findAll(){return boatRepository.findAll();}

    public List<Boat> findAllByOwnerEmail(String email){return boatRepository.findAllByOwnerEmail(email);}


    public Boat findOne(Integer id){return boatRepository.findById(id).orElse(null);}

    public List<BoatDTO> getAllBoats(){
        List<Boat> boats = findAll();
        List<BoatDTO> boatsDTO = new ArrayList<>();
        for (Boat b : boats){
            if (!b.isDeleted()){
                boatsDTO.add(mapper.map(b, BoatDTO.class));
            }}
        return boatsDTO;
    }

    public Boat createBoat(BoatCreateDTO boatCreateDTO,String email) throws IOException {
        Boat boat = mapper.map(boatCreateDTO,Boat.class);
        BoatOwner boatOwner = boatOwnerRepository.findByEmail(email);
        boat.setBoatOwner(boatOwner);
        boat.setAddress(new Address(boatCreateDTO.getStreet(),boatCreateDTO.getCity(),boatCreateDTO.getPostal_code(),boatCreateDTO.getCountry()));
        boat.setType(BoatType.getTypeFromString(boatCreateDTO.getType()));
        boat.setKind(EntityKind.BOAT);

        Set<Rule> rules = ruleService.createRuleFromString(boatCreateDTO.getRules(),boat);
        boat.setRules(rules);

        Set<FishingEquipment> fishingEquipments = equipmentService.createFishingEquipmentFromString(boatCreateDTO.getFishingEquipment(),boat);
        boat.setFishingEquipment(fishingEquipments);

        Set<AdditionalService> services = additionalServiceService.createAddServiceFromString(boatCreateDTO.getAdditionalServices(),boat);
        boat.setAdditionalServices(services);

        Set<Image> images = imageService.createImageFromString(boatCreateDTO.getImages(),boat);
        boat.setImages(images);

        Set<NavigationEquipment> navigationEquipments = navigationEquipmentService.createNavigationEquipmentFromString(boatCreateDTO.getNavigationEquipment(),boat);
        boat.setNavigationEquipment(navigationEquipments);

        save(boat);

        equipmentService.addFishingEquipments(fishingEquipments);
        navigationEquipmentService.addNavigationEquipments(navigationEquipments);
        ruleService.addRules(rules);
        additionalServiceService.addAdditionalServices(services);
        imageService.addImages(images);
        return boat;
    }

    public Boat updateBoat(BoatDTO boatDTO){
        Boat boat = findOne(boatDTO.getId());
        boat.setName(boatDTO.getName());
        boat.setPrice(boatDTO.getPrice());
        boat.setCapacity(boatDTO.getCapacity());
        boat.setDescription(boatDTO.getDescription());
        boat.setMaxSpeed(boatDTO.getMaxSpeed());
        boat.setLength(boatDTO.getLength());
        boat.setPower(boatDTO.getPower());
        boat.setMotorNum(boatDTO.getMotorNum());
        boat.setCancelFee(boatDTO.getCancelFee());
        Address a = mapper.map(boatDTO.getAddress(),Address.class);
        boat.setAddress(a);
        save(boat);
        return boat;
    }

    public ResponseEntity<String> deleteBoat(Integer id){
        try {
            Boat boat = findOne(id);
            if(boat == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if(!canDeleteBoat(boat)){
                return new ResponseEntity<>("Boat has reservations.Deletion is not possible.",HttpStatus.BAD_REQUEST);
            }
            deleteBoat(boat);
            return new ResponseEntity<>("Deletion is successful.",HttpStatus.OK);
        }catch (PessimisticLockingFailureException e){
            return new ResponseEntity<>("Someone just made reservation.",HttpStatus.CONFLICT);
        }
    }


    public Double getBoatsAverageGrade(String email){
        List<Boat> boats = findAllByOwnerEmail(email);
        double averageGrade = 0.0;
        for (Boat b : boats){
            averageGrade += b.getAverageGrade();
        }
        averageGrade = averageGrade / boats.size();
        return averageGrade;
    }

    public List<BoatDTO> getAvailableBoats(EntitySearchDTO params,String email){
        List<Boat> entities;
        List<BoatDTO> entitiesDTO = new ArrayList<>();
        LocalDateTime start = LocalDateTime.of(params.getStartDate(), params.getStartTime());
        LocalDateTime end = LocalDateTime.of(params.getEndDate(), params.getEndTime());

        entities = findAllByOwnerEmail(email);
        for(Boat c: entities){
            if (c.getName().contains(params.getName()) && c.getPrice() <= params.getPrice() && c.getAddress().getCity().equals(params.getCity())){
                if(availablePeriodService.isAvailable(c.getId(), start, end) && reservationsRepository.getReserved(c.getId(), start, end).isEmpty()){
                    BoatDTO dto = mapper.map(c, BoatDTO.class);
                    entitiesDTO.add(dto);
                }
            }
        }
        return entitiesDTO;
    }

    public List<BoatDTO> getAllBoatsByOwner(String email){
        List<Boat> boats = findAllByOwnerEmail(email);
        List<BoatDTO> boatsDTO = new ArrayList<>();
        for (Boat b : boats){
            boatsDTO.add(mapper.map(b, BoatDTO.class));
        }
        return boatsDTO;
    }

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
