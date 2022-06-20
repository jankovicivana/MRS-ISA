package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.dto.CottageCreateDTO;
import ftn.mrs.isa.rentalapp.dto.CottageDTO;
import ftn.mrs.isa.rentalapp.dto.EntitySearchDTO;
import ftn.mrs.isa.rentalapp.model.entity.*;
import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.model.user.CottageOwner;
import ftn.mrs.isa.rentalapp.repository.CottageOwnerRepository;
import ftn.mrs.isa.rentalapp.repository.CottageRepository;
import ftn.mrs.isa.rentalapp.repository.QuickReservationRepository;
import ftn.mrs.isa.rentalapp.repository.ReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CottageService {

    @Autowired
    private CottageRepository cottageRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private AdditionalServiceService additionalServiceService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private AvailablePeriodService availablePeriodService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CottageOwnerRepository cottageOwnerRepository;

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

    public List<CottageDTO> getAllCottages(){
        List<Cottage> cottages = cottageRepository.findAll();
        List<CottageDTO> cottagesDTO = new ArrayList<>();
        for (Cottage c : cottages){
            if (!c.isDeleted()){
                cottagesDTO.add(mapper.map(c,CottageDTO.class));
            }}
        return  cottagesDTO;
    }

    public List<CottageDTO> getAllCottagesByOwner(String email){
        List<Cottage> cottages = cottageRepository.findAllByOwnerEmail(email);
        List<CottageDTO> cottagesDTO = new ArrayList<>();
        for (Cottage c : cottages){
            cottagesDTO.add(mapper.map(c,CottageDTO.class));
        }
        return  cottagesDTO;
    }

    public Cottage createCottage(CottageCreateDTO cottageCreateDTO,String ownerEmail) throws IOException {
        Cottage cottage = new Cottage();
        CottageOwner cottageOwner = cottageOwnerRepository.findByEmail(ownerEmail);
        cottage.setCottageOwner(cottageOwner);
        cottage.setName(cottageCreateDTO.getName());
        cottage.setDescription(cottageCreateDTO.getDescription());
        cottage.setMaxNumPerson(cottageCreateDTO.getMaxNumPerson());
        cottage.setPrice(cottageCreateDTO.getPrice());
        cottage.setAddress(new Address(cottageCreateDTO.getStreet(),cottageCreateDTO.getCity(),Integer.parseInt(cottageCreateDTO.getPostalCode()),cottageCreateDTO.getCountry()));
        cottage.setKind(EntityKind.COTTAGE);

        Set<Room> rooms =  roomService.createRoomFromString(cottageCreateDTO.getRooms(),cottage);
        cottage.setRooms(rooms);

        Set<Rule> rules = ruleService.createRuleFromString(cottageCreateDTO.getRules(),cottage);
        cottage.setRules(rules);

        Set<AdditionalService> services = additionalServiceService.createAddServiceFromString(cottageCreateDTO.getAdditionalServices(),cottage);
        cottage.setAdditionalServices(services);

        Set<Image> images = imageService.createImageFromString(cottageCreateDTO.getImages(),cottage);
        cottage.setImages(images);

        cottageRepository.save(cottage);
        roomService.addRooms(rooms);
        ruleService.addRules(rules);
        additionalServiceService.addAdditionalServices(services);
        imageService.addImages(images);
        return cottage;
    }

    public Cottage updateCottage(CottageDTO cottageDTO){
        Cottage cottage = findOne(cottageDTO.getId());
        cottage.setName(cottageDTO.getName());
        cottage.setPrice(cottageDTO.getPrice());
        cottage.setMaxNumPerson(cottageDTO.getMaxNumPerson());
        cottage.setDescription(cottageDTO.getDescription());
        Address a = mapper.map(cottageDTO.getAddress(),Address.class);
        cottage.setAddress(a);
        cottageRepository.save(cottage);
        return cottage;
    }

    public Double getCottagesAverageGrade(String email){
        List<Cottage> cottages = findAllByOwnerEmail(email);
        double averageGrade = 0.0;
        for (Cottage c : cottages){
            averageGrade += c.getAverageGrade();
        }
        averageGrade = averageGrade / cottages.size();
        return averageGrade;
    }

    public List<CottageDTO> getAvailableCottages(EntitySearchDTO params,String email){
        List<Cottage> entities;
        List<CottageDTO> entitiesDTO = new ArrayList<>();
        LocalDateTime start = LocalDateTime.of(params.getStartDate(), params.getStartTime());
        LocalDateTime end = LocalDateTime.of(params.getEndDate(), params.getEndTime());
        entities = findAllByOwnerEmail(email);
        for(Cottage c: entities){
            if (c.getName().contains(params.getName()) && c.getPrice() <= params.getPrice() && c.getAddress().getCity().equals(params.getCity())){
                if(availablePeriodService.isAvailable(c.getId(), start, end) && reservationRepository.getReserved(c.getId(), start, end).isEmpty()){
                    CottageDTO dto = mapper.map(c, CottageDTO.class);
                    dto.setType("Cottage");
                    entitiesDTO.add(dto);
                }
            }
        }
        return entitiesDTO;
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