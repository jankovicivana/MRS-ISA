package ftn.mrs.isa.rentalapp.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class QuickReservationDTO {

    private Integer id;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private LocalDateTime expirationDateTime;

    private Integer maxPersonNum;

    private Double price;

    private Integer discount;

    private Double discountedPrice;

    private Integer entId;

}