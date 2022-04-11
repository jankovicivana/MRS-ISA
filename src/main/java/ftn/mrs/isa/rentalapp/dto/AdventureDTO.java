package ftn.mrs.isa.rentalapp.dto;


import lombok.Data;

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


}
