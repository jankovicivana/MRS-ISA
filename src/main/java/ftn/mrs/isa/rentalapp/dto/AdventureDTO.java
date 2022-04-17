package ftn.mrs.isa.rentalapp.dto;


import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class AdventureDTO {

    private String name;
    private Double price;
    private Integer maxPersonNum;
    private Integer cancelFee;
    private String description;
    private String country;
    private String city;
    private String street;
    private Integer postal_code;
    private Set<String> images = new HashSet<>();
    private Set<String> rules = new HashSet<>();
    private Set<String> additionalServices = new HashSet<>();
    private  Set<String> fishingEquipment = new HashSet<>();

}
