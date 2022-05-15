package ftn.mrs.isa.rentalapp.dto;


import ftn.mrs.isa.rentalapp.model.entity.Adventure;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.model.user.FishingInstructor;
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
}
