package ftn.mrs.isa.rentalapp.model.system_info;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "rankingInfo")
public class RankingInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "rank", nullable = false)
    private RankType rank;

    @Column(name = "pointsLimit", nullable = false)
    private Integer pointsLimit;

    @Column(name = "clientDiscount", nullable = false)
    private Integer clientDiscount;

    @Column(name = "advertiserBenefits", nullable = false)
    private Integer advertiserBenefits;




}
