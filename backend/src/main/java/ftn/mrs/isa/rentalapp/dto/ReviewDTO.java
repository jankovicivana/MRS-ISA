package ftn.mrs.isa.rentalapp.dto;

import lombok.Data;

@Data
public class ReviewDTO {

    private Integer entityGrade;
    private String entityReview;

    private Integer ownerGrade;
    private String ownerReview;

    private Integer clientId;
    private Integer entityId;

    private Integer reservationId;
}
