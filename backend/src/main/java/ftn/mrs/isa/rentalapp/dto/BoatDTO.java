package ftn.mrs.isa.rentalapp.dto;

import ftn.mrs.isa.rentalapp.model.entity.BoatType;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
public class BoatDTO {

    private Integer id;

    private String name;

    private Double price;

    private AddressDTO address;

    private String description;

    private Set<ImageDTO> images;

    private String boatOwnerName;

    private Set<RuleDTO> rules;

    private Set<AdditionalServiceDTO> additionalServices;

    private Integer capacity;

    private Set<QuickReservationDTO> quickReservations;

    private String type;

    private Double length;

    private Integer motorNum;

    private Integer power;

    private Integer maxSpeed;

    private Integer cancelFee;

    private  Set<FishingEquipmentDTO> fishingEquipment = new HashSet<>();

    private Set<NavigationEquipmentDTO> navigationEquipment = new HashSet<>();
}
