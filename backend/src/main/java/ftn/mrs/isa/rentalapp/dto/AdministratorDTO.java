package ftn.mrs.isa.rentalapp.dto;

import ftn.mrs.isa.rentalapp.model.user.UserType;
import lombok.Data;

@Data
public class AdministratorDTO {

    private Integer id;
    private Boolean isPredefined = false;
    private String email;
    private String password;
    private String name;
    private String surname;
    private AddressDTO address;
    private String phoneNumber;
    private UserType type = UserType.ADMINISTRATOR;
    private boolean enabled;
    private boolean deleted;
    private String mainPhoto;
    private boolean passwordChanged;




}
