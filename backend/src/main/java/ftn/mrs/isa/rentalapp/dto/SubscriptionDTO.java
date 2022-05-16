package ftn.mrs.isa.rentalapp.dto;

import lombok.Data;

@Data
public class SubscriptionDTO {

    private Integer id;

    private ClientDTO client;

    private EntityTypeDTO entity;

}
