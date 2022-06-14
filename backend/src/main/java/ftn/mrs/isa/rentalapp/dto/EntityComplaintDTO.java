package ftn.mrs.isa.rentalapp.dto;

import lombok.Data;

@Data
public class EntityComplaintDTO {

    private Integer id;
    private String complaint;
    private String answer;
    private ClientDTO client;
    private EntityDTO entity;
}
