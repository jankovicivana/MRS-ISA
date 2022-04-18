package ftn.mrs.isa.rentalapp.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class AdventureCreateDTO {

        private String name;
        private Double price;
        private  String maxPersonNum;
        private String city;
        private  String description;
        private String street;
        private Integer postal_code;
        private  String country;
        private Integer cancelFee;
        private Set<String> images = new HashSet<>();
        private Set<String> rules = new HashSet<>();
        private Set<String> additionalServices = new HashSet<>();
        private  Set<String> fishingEquipment = new HashSet<>();


}
