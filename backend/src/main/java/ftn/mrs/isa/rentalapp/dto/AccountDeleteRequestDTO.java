package ftn.mrs.isa.rentalapp.dto;


import ftn.mrs.isa.rentalapp.model.user.User;
import lombok.Data;

@Data
public class AccountDeleteRequestDTO {

    private Integer id;
    private String answer;
    private String requestReason;
    private UserRequest user;

}
