package ftn.mrs.isa.rentalapp.dto;

import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import lombok.Data;

@Data
public class AdvertiserDTO {

    private Integer id;

    private String email;

    private String password;

    private String name;

    private String surname;

    private String phoneNumber;

    private String biography;

    private AddressDTO address;

    private String mainPhoto;

    private RequestStatus registrationStatus;


}
