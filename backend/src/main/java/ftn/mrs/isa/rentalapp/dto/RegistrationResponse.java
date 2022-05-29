package ftn.mrs.isa.rentalapp.dto;


import lombok.Data;

@Data
public class RegistrationResponse {

    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String reason;
}
