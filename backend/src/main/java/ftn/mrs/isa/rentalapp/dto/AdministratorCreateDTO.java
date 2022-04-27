package ftn.mrs.isa.rentalapp.dto;

import ftn.mrs.isa.rentalapp.model.user.UserType;
import lombok.Data;

@Data
public class AdministratorCreateDTO {

    private Boolean isPredefined = false;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String street;
    private Integer postal_code;
    private  String country;
    private String city;
    private String phoneNumber;
    private UserType type;


}
