package ftn.mrs.isa.rentalapp.dto;

import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class QuickReservationDTO {

    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer maxPersonNum;

    private Double beforePrice;

    private Double afterPrice;

    private Integer discount;

    public QuickReservationDTO(QuickReservation quickReservation){
        this.id = quickReservation.getId();
        this.startDate = quickReservation.getStartDateTime().toLocalDate();
        this.endDate = quickReservation.getEndDateTime().toLocalDate();
        this.maxPersonNum = quickReservation.getMaxPersonNum();
        this.beforePrice = quickReservation.getPrice();
        this.discount = quickReservation.getDiscount();
        this.afterPrice = this.beforePrice - (this.beforePrice * discount/100);

    }
}