package ftn.mrs.isa.rentalapp.dto;

import lombok.Data;

@Data
public class ReportCreateDTO {

    private Integer clientId;

    private Integer advertiserId;

    private Boolean didShowUp;

    private Boolean sanction;

    private String reportString;
}
