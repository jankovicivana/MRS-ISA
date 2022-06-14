package ftn.mrs.isa.rentalapp.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class AdventureDTO {

    private Integer id;
    private String name;
    private AddressDTO address;
    private Double price;
    private Integer maxPersonNum;
    private Integer cancelFee;
    private  String description;
    private  FishingInstructorDTO fishingInstructor;
    private Set<ImageDTO> images = new HashSet<>();
    private Set<RuleDTO> rules = new HashSet<>();
    private Set<AdditionalServiceDTO> additionalServices = new HashSet<>();
    private  Set<FishingEquipmentDTO> fishingEquipment = new HashSet<>();
    private Set<QuickReservationDTO> quickReservations = new HashSet<QuickReservationDTO>();
    private Double averageGrade ;

}
