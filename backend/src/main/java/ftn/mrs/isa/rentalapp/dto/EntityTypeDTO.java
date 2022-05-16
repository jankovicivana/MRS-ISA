package ftn.mrs.isa.rentalapp.dto;

import lombok.Data;

@Data
public class EntityTypeDTO {

    private Integer id;

    private String name;

    private Double price;

    private AddressDTO address;

    private String description;

}
