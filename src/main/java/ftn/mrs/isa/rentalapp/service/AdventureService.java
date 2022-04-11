package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.dto.AdventureDTO;
import ftn.mrs.isa.rentalapp.model.entity.*;
import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.model.user.FishingInstructor;
import ftn.mrs.isa.rentalapp.model.user.UserType;
import ftn.mrs.isa.rentalapp.repository.AdventureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service

public class AdventureService {

    @Autowired
    private  AdventureRepository adventureRepository;




    public void addAdventure(AdventureDTO adventureDto) {
        //nadji info instruktora
        Adventure adventure = new Adventure();
        adventure.setName(adventureDto.getName());
        adventure.setDescription(adventureDto.getDescription());
        adventure.setPrice(adventureDto.getPrice());
        Address a = new Address();
        a.setCountry(adventureDto.getCountry());
        a.setCity(adventureDto.getCity());
        a.setStreet(adventureDto.getStreet());
        a.setPostalCode(adventureDto.getPostal_code());
        adventure.setAddress(a);
        adventure.setCancelFee(adventureDto.getCancelFee());
        adventure.setMaxPersonNum(adventureDto.getMaxPersonNum());
        adventure.setFishingEquipment(new HashSet<FishingEquipment>());
        FishingInstructor f= new FishingInstructor();
        f.setAddress(a);
        f.setEmails(new HashSet<>());
        f.setEmail("dd");
        f.setName("ime");
        f.setPoints(0);
        f.setPassword("pass");
        f.setPhoneNumber("5646");
        f.setSurname("sdf");
        f.setBiography("nema");
        f.setRegistrationStatus(RequestStatus.ACCEPTED);
        f.setType(UserType.FISHING_INSTRUCTOR);
        f.setAccountDeleteRequest(null);
        f.setComplaints(new HashSet<>());
        adventure.setFishingInstructor(f); //dok ne postavim ulogovanog
        adventure.setAdditionalServices(new HashSet<AdditionalService>());
        adventure.setAvailablePeriods(new HashSet<AvailablePeriod>());
        adventure.setComplaints(new HashSet<EntityComplaint>());
        adventure.setImages(new HashSet<Image>());
        adventure.setQuickReservations(new HashSet<QuickReservation>());
        adventure.setReservations(new HashSet<Reservation>());
        adventure.setReviews(new HashSet<EntityReview>());
        adventure.setRules(new HashSet<Rule>());
        adventure.setSubscriptions(new HashSet<Subscription>());
        adventure.setId(1);
        System.out.print(adventureRepository);
        adventureRepository.save(adventure);
    };


}
