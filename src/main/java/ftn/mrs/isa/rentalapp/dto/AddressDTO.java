package ftn.mrs.isa.rentalapp.dto;

import lombok.Data;

@Data
public class AddressDTO {

    private Integer id;

    private String street;

    private String country;

    private String city;

    private String postal_code;

}
