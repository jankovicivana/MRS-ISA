package ftn.mrs.isa.rentalapp.model.system_info;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "systemInfo")
public class SystemInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "clientPoints", nullable = false)
    private Integer clientPoints;

    @Column(name = "advertiserPoints", nullable = false)
    private Integer advertiserPoints;

    @Column(name = "systemProfit", nullable = false)
    private Integer systemProfit;

}
