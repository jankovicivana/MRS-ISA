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

    private String address;

    private String description;

    private String mainPhotoUrl;

    private Set<String> imagesUrls = new HashSet<String>();

    private String cottageOwnerName;

    private Set<String> rules = new HashSet<String>();

    private Set<String> additionalServices = new HashSet<String>();

    private Integer numOfRooms;

    private Integer numOfBeds = 0;

    private Set<QuickReservationDTO> quickReservationsDTO = new HashSet<QuickReservationDTO>();


    public CottageDTO(Cottage cottage){
        this.id = cottage.getId();
        this.name = cottage.getName();
        this.price = cottage.getPrice();
        this.address = cottage.getAddress().getStreet();
        this.description = cottage.getDescription();

        Set<Image> images = cottage.getImages();

        List<Image> urls = new ArrayList<Image>(images);
        this.mainPhotoUrl = urls.get(0).getPath();

        for (Image i : images){
            if (!i.getPath().equals(this.mainPhotoUrl)) {
                this.imagesUrls.add(i.getPath());
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

        Set<QuickReservation> quickReservations = cottage.getQuickReservations();
        for (QuickReservation q : quickReservations) {
            this.quickReservationsDTO.add(new QuickReservationDTO(q));
        }
    }

}
