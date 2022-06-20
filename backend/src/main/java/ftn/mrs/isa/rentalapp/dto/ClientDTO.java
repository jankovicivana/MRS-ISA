package ftn.mrs.isa.rentalapp.dto;

import ftn.mrs.isa.rentalapp.model.user.UserType;
import lombok.Data;

@Data
public class ClientDTO {

    private Integer id;

    private String email;

    private String password;

    private String name;

    private String surname;

    private AddressDTO address;

    private String phoneNumber;

    private UserType type = UserType.CLIENT;

    private String mainPhoto;

    private Integer points;

    private Integer penalties;

    private Boolean deleted;

}
