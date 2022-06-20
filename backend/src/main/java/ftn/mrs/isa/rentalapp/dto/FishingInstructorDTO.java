package ftn.mrs.isa.rentalapp.dto;

import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import ftn.mrs.isa.rentalapp.model.user.UserType;
import lombok.Data;

@Data
public class FishingInstructorDTO {
    private Integer id;

    private String email;

    private String password;

    private String name;

    private String surname;

    private String phoneNumber;

    private String biography;

    private AddressDTO address;

    private String mainPhoto;

    private String registrationReason;

    private UserType type = UserType.FISHING_INSTRUCTOR;

    private RequestStatus registrationStatus;

}
