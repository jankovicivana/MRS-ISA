package ftn.mrs.isa.rentalapp.dto;

import ftn.mrs.isa.rentalapp.model.system_info.RankType;
import lombok.Data;

@Data
public class RankingInfoDTO {

    private Integer id;
    private RankType rank;
    private Integer pointsLimit;
    private Integer clientDiscount;
    private Integer advertiserBenefits;

}
