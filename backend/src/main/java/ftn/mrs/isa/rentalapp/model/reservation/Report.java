package ftn.mrs.isa.rentalapp.model.reservation;


import ftn.mrs.isa.rentalapp.model.user.Advertiser;
import ftn.mrs.isa.rentalapp.model.user.Client;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "reports")
public class Report {
    @Id
    @SequenceGenerator(name = "mySeqGenV5", sequenceName = "mySeqV5", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV5")
    private Integer id;

    @Column(name = "reportString")
    private String reportString;

    @Column(name = "penaltyStatus", nullable = false)
    private RequestStatus penaltyStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "advertiser")
    private Advertiser advertiser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client")
    private Client client;

    @Column(name = "clientShowedUp", nullable = false)
    private Boolean clientShowedUp;


}
