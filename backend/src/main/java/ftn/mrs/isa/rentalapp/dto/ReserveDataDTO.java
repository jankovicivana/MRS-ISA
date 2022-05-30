package ftn.mrs.isa.rentalapp.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReserveDataDTO {

    private Integer entityId;

    private Integer personNum;

    private LocalDate startDate;

    private LocalTime startTime;

    private LocalDate endDate;

    private LocalTime endTime;
}
