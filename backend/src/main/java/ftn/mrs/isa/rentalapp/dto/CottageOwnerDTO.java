package ftn.mrs.isa.rentalapp.dto;

import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import ftn.mrs.isa.rentalapp.model.user.UserType;
import lombok.Data;

@Data
public class CottageOwnerDTO {

    private Integer id;

    private String email;

    private String password;

    private String name;

    private String surname;

    private String phoneNumber;

    private String biography;

    private AddressDTO address;

    private String mainPhoto;

    private UserType type = UserType.COTTAGE_OWNER;

    private RequestStatus registrationStatus;


}
