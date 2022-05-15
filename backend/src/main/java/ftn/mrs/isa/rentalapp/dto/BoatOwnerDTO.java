package ftn.mrs.isa.rentalapp.dto;

import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import ftn.mrs.isa.rentalapp.model.user.UserType;
import lombok.Data;

@Data
public class BoatOwnerDTO {
    private Integer id;

    private String email;

    private String password;

    private String name;

    private String surname;

    private String phoneNumber;

    private AddressDTO address;

    private String mainPhoto;

    private UserType type = UserType.BOAT_OWNER;

    private RequestStatus registrationStatus;


}
