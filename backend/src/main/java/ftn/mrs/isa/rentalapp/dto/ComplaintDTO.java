package ftn.mrs.isa.rentalapp.dto;

import lombok.Data;

@Data
public class ComplaintDTO {

    private String entityComplaint;

    private String ownerComplaint;

    private Integer clientId;
    private Integer entityId;

    private Integer reservationId;
}
