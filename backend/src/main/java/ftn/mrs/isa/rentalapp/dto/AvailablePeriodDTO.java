package ftn.mrs.isa.rentalapp.dto;


import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AvailablePeriodDTO {
    private Integer id;
    private LocalDate startDateTime;
    private LocalDate endDateTime;


}
