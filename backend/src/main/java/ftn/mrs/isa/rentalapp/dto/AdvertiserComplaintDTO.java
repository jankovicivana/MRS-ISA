package ftn.mrs.isa.rentalapp.dto;

import lombok.Data;

@Data
public class AdvertiserComplaintDTO {

    private Integer id;
    private String complaint;
    private String answer;
    private ClientDTO client;
    private AdvertiserDTO advertiser;

}
