package ftn.mrs.isa.rentalapp.dto;

import lombok.Data;

@Data
public class UserRequest {

    private Long id;

    private String email;

    private String password;

    private String name;

    private String surname;

    private String city;

    private String street;

    private String country;

    private Integer postalCode;

    private String role;

    private String phoneNumber;

    private String reason;

    private String biography;
}
