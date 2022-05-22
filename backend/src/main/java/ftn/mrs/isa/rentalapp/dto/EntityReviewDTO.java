package ftn.mrs.isa.rentalapp.dto;

import lombok.Data;

@Data
public class EntityReviewDTO {

    private Integer id;
    private Integer grade;
    private String review;
    private ClientDTO client;
    private EntityDTO entity;

}
