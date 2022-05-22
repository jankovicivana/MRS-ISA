package ftn.mrs.isa.rentalapp.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class QuickReservationDTO {

    private Integer id;

    private LocalDate startDateTime;

    private LocalDate endDateTime;

    private LocalDate expirationDateTime;

    private Integer maxPersonNum;

    private Double price;

    private Integer discount;

    private Double discountedPrice;

    private Integer entId;

}