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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "reportString")
    private String reportString;

    @Column(name = "penaltyStatus", nullable = false)
    private RequestStatus penaltyStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertiser")
    private Advertiser advertiser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client")
    private Client client;

    @Column(name = "clientShowedUp", nullable = false)
    private Boolean clientShowedUp;


}
