package ftn.mrs.isa.rentalapp.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class AvailablePeriodDTO {
    //private Integer id;
    private LocalDate startDateTime;
    private LocalDate endDateTime;
    private Integer entity;
    private Integer fishingInstructor;

}
