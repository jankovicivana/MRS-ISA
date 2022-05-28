package ftn.mrs.isa.rentalapp.dto;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AvailablePeriodDTO {
    //private Integer id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Integer entity;
    private Integer fishingInstructor;

}
