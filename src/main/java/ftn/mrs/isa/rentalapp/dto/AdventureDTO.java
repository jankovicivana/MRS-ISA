package ftn.mrs.isa.rentalapp.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class AdventureDTO {

    private String name;
    private String city;
    private String street;
    private Integer postal_code;
    private Integer maxPersonNum;
    private Integer cancelFee;
    private  String description;
    private  String biography;
    private Set<ImageDTO> images = new HashSet<>();
    private Set<RuleDTO> rules = new HashSet<>();
    private Set<AdditionalServiceDTO> additionalServices = new HashSet<>();
    private  Set<FishingEquipmentDTO> fishingEquipment = new HashSet<>();
    private Set<QuickReservationDTO> quickReservations = new HashSet<QuickReservationDTO>();


}
