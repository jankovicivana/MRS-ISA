package ftn.mrs.isa.rentalapp.dto;


import lombok.Data;

@Data
public class ReportDTO {

    private Integer id;

    private ClientDTO client;

    private AdvertiserDTO advertiser;

    private String reportString;

}
