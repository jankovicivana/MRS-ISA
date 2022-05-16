package ftn.mrs.isa.rentalapp.dto;

import ftn.mrs.isa.rentalapp.model.entity.Subscription;
import ftn.mrs.isa.rentalapp.model.system_info.Email;
import ftn.mrs.isa.rentalapp.model.user.AccountDeleteRequest;
import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.model.user.UserType;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

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
}
