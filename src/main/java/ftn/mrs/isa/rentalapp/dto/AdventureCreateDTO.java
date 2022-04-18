package ftn.mrs.isa.rentalapp.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class AdventureCreateDTO {

        private String city;
        private String street;
        private Integer postal_code;

        private Set<String> images = new HashSet<>();
        private Set<String> rules = new HashSet<>();
        private Set<String> additionalServices = new HashSet<>();
        private  Set<String> fishingEquipment = new HashSet<>();


}
