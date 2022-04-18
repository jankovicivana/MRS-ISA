package ftn.mrs.isa.rentalapp.dto;

import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class QuickReservationDTO {

    private Long id;

    private LocalDate startDateTime;

    private LocalDate endDateTime;

    private Integer maxPersonNum;

    private Double price;

    private Integer discount;

    private Double discountedPrice;
}