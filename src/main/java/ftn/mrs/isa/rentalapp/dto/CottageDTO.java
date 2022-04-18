package ftn.mrs.isa.rentalapp.dto;


import ftn.mrs.isa.rentalapp.model.entity.*;
import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class CottageDTO {

    private Integer id;

    private String name;

    private Double price;

    private String street;

    private String country;

    private String city;

    private String postal_code;

    private String description;

    private String mainPhotoUrl;

    private Set<String> images = new HashSet<String>();

    private String cottageOwnerName;

    private Set<String> rules = new HashSet<String>();

    private Set<String> additionalServices = new HashSet<String>();

    private Set<String> rooms;

    private Integer maxNumPerson;

    private Integer numOfRooms;

    private Integer numOfBeds = 0;

    private Set<QuickReservationDTO> quickReservationsDTO = new HashSet<QuickReservationDTO>();

    public CottageDTO(){

    }

    public CottageDTO(Cottage cottage){
        this.id = cottage.getId();
        this.name = cottage.getName();
        this.price = cottage.getPrice();
        this.city = cottage.getAddress().getCity();
        this.country = cottage.getAddress().getCountry();
        this.postal_code = cottage.getAddress().getPostalCode().toString();
        this.street = cottage.getAddress().getStreet();
        this.description = cottage.getDescription();

        Set<Image> imagess = cottage.getImages();

        List<Image> urls = new ArrayList<Image>(imagess);
        this.mainPhotoUrl = urls.get(0).getPath();

        for (Image i : imagess){
            if (!i.getPath().equals(this.mainPhotoUrl)) {
                this.images.add(i.getPath());
            }
        }

        this.cottageOwnerName = cottage.getCottageOwner().getName();
        for(Rule r : cottage.getRules()){
            this.rules.add(r.getRule());
        }
        this.numOfRooms = cottage.getRooms().size();
        for(Room room : cottage.getRooms()){
            this.numOfBeds += room.getBedNumber();
        }
        Set<AdditionalService> services = cottage.getAdditionalServices();
        for(AdditionalService a : services){
            this.additionalServices.add(a.getName());
        }

        //Set<QuickReservation> quickReservations = cottage.getQuickReservations();
        //for (QuickReservation q : quickReservations) {
         //   this.quickReservationsDTO.add(new QuickReservationDTO(q));
        //}
    }

}
