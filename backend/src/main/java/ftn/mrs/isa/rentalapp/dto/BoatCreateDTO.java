package ftn.mrs.isa.rentalapp.dto;

import ftn.mrs.isa.rentalapp.model.entity.BoatType;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class BoatCreateDTO {

    private Integer id;
    private String name;
    private Double price;
    private String city;
    private String street;
    private Integer postal_code;
    private String country;
    private String description;
    private Set<String> images;
    private String boatOwnerName;
    private Set<String> rules;
    private Set<String> additionalServices;
    private Integer capacity;
    private String type;
    private Double length;
    private Integer motorNum;
    private Integer power;
    private Integer maxSpeed;
    private Integer cancelFee;
    private  Set<String> fishingEquipment = new HashSet<>();
    private Set<String> navigationEquipment = new HashSet<>();
}
