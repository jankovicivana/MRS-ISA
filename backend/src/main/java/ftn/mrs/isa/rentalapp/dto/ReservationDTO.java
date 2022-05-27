package ftn.mrs.isa.rentalapp.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationDTO {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private ClientDTO client;
    private Double price;
    private Integer personNum;
    private AdventureDTO adventure;
    private CottageDTO cottage;

    private EntityTypeDTO entity;
}
