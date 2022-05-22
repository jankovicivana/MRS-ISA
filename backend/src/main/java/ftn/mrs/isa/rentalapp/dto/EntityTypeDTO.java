package ftn.mrs.isa.rentalapp.dto;

import lombok.Data;

import java.util.Set;

@Data
public class EntityTypeDTO {

    private Integer id;

    private String name;

    private Double price;

    private AddressDTO address;

    private String description;

    private Set<ImageDTO> images;

    private Set<AdditionalServiceDTO> additionalServices;

}
