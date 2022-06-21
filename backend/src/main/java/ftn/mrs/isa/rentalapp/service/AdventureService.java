package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.dto.AdventureCreateDTO;
import ftn.mrs.isa.rentalapp.dto.AdventureDTO;
import ftn.mrs.isa.rentalapp.model.entity.*;
import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.model.user.FishingInstructor;
import ftn.mrs.isa.rentalapp.repository.AdventureRepository;
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
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class AdventureService {

    @Autowired
    private AdventureRepository adventureRepository;

    @Autowired
    private ReservationRepository reservationsRepository;

    @Autowired
    private QuickReservationRepository quickReservationRepository;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private AdditionalServiceService additionalServiceService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ModelMapper mapper;

    public Adventure findOne(Integer id){return adventureRepository.findById(id).orElse(null);}

    public List<Adventure> findAll(){return adventureRepository.findAll();}

    public List<Adventure> findAllByOwnerEmail(String email){return adventureRepository.findAllByOwnerEmail(email);}

    public void save(Adventure adventure){adventureRepository.save(adventure);}


    public boolean canDeleteAdventure(Adventure adventure) {
        List<Reservation> l = reservationsRepository.getReservationByEntity(LocalDate.now(), adventure.getId());
        return l.size() == 0;
    }

    public void deleteAdventure(Adventure adventure) {
        adventure.setDeleted(true);
        adventureRepository.save(adventure);
        for(Reservation r : adventure.getReservations()){
            r.setDeleted(true);
        }
        reservationsRepository.saveAll(adventure.getReservations());

        for(QuickReservation r : adventure.getQuickReservations()){
            r.setDeleted(true);
        }
        quickReservationRepository.saveAll(adventure.getQuickReservations());
    }


    public void saveAll(Set<Adventure> adventures) {
        adventureRepository.saveAll(adventures);
    }

    public List<Adventure> findAllByOwnerId(Integer id) {
        return adventureRepository.findAllByOwnerId(id);
    }

    public Adventure createAdventure(FishingInstructor instructor, AdventureCreateDTO adventureDTO) throws IOException {
        Adventure adventure = new Adventure();
        adventure = setMainAdventureInfo(adventure,adventureDTO,instructor);
        adventure = setAdditionalInfo(adventure,adventureDTO);
        return adventure;
    }


    public ResponseEntity<String> deleteAdventure(Integer id){
        try{
            Adventure adventure = findOne(id);
            if(adventure == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (!canDeleteAdventure(adventure)){
                return new ResponseEntity<>("Adventure has reservations.Deletion is not possible.",HttpStatus.BAD_REQUEST);
            }
            deleteAdventure(adventure);
            return new ResponseEntity<>("Deletion is successful.",HttpStatus.OK);
        }catch (PessimisticLockingFailureException e){
            return new ResponseEntity<>("Someone just made reservation.",HttpStatus.CONFLICT);
        }
    }


    public Adventure setMainAdventureInfo(Adventure adventure, AdventureCreateDTO adventureDTO,FishingInstructor instructor){
        adventure.setName(adventureDTO.getName());
        adventure.setDescription(adventureDTO.getDescription());
        adventure.setMaxPersonNum(Integer.parseInt(adventureDTO.getMaxPersonNum()));
        adventure.setPrice(adventureDTO.getPrice());
        adventure.setCancelFee(adventureDTO.getCancelFee());
        adventure.setAddress(new Address(adventureDTO.getStreet(),adventureDTO.getCity(),adventureDTO.getPostal_code(),adventureDTO.getCountry()));
        adventure.setFishingInstructor(instructor);
        adventure.setKind(EntityKind.ADVENTURE);
        return adventure;
    }

    public Adventure setAdditionalInfo(Adventure adventure,AdventureCreateDTO adventureDTO) throws IOException {
        Set<Rule> rules = ruleService.createRuleFromString(adventureDTO.getRules(),adventure);
        adventure.setRules(rules);

        Set<AdditionalService> services = additionalServiceService.createAddServiceFromString(adventureDTO.getAdditionalServices(),adventure);
        adventure.setAdditionalServices(services);

        Set<Image> images = imageService.createImageFromString(adventureDTO.getImages(),adventure);
        adventure.setImages(images);

        Set<FishingEquipment> equipment = equipmentService.createFishingEquipmentFromString(adventureDTO.getFishingEquipment(),adventure);
        adventure.setFishingEquipment(equipment);


        save(adventure);
        ruleService.addRules(rules);
        additionalServiceService.addAdditionalServices(services);
        imageService.addImages(images);
        return adventure;
    }

    public Adventure updateAdventure(Adventure adventure, AdventureDTO adventureDTO) {
        adventure.setName(adventureDTO.getName());
        adventure.setPrice(adventureDTO.getPrice());
        adventure.setMaxPersonNum(adventureDTO.getMaxPersonNum());
        adventure.setDescription(adventureDTO.getDescription());
        adventure.setCancelFee(adventureDTO.getCancelFee());
        Address a = mapper.map(adventureDTO.getAddress(),Address.class);
        adventure.setAddress(a);
        save(adventure);
        return adventure;
    }
}
