package ftn.mrs.isa.rentalapp.dto;

import ftn.mrs.isa.rentalapp.model.user.Client;
import lombok.Data;

@Data
public class ClientDTO {

    private Integer id;

    private String email;

    private String password;

    private String name;

    private String surname;

    private String phoneNumber;

    private String country;

    private String city;

    private String street;

    private Integer postalCode;

    private Integer points;

    private Integer penalties;

    public ClientDTO(Client client){
        this.id = client.getId();
        this.email = client.getEmail();
        this.password = client.getPassword();
        this.name = client.getName();
        this.surname = client.getSurname();
        this.phoneNumber = client.getPhoneNumber();
        this.country = client.getAddress().getCountry();
        this.city = client.getAddress().getCity();
        this.street = client.getAddress().getStreet();
        this.postalCode = client.getAddress().getPostalCode();
        this.penalties = client.getPenalties();
        this.points = client.getPoints();

    }

}
