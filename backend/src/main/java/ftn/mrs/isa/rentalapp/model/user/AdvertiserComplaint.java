package ftn.mrs.isa.rentalapp.model.user;


import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "advertiserComplaints")
public class AdvertiserComplaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "complaint", nullable = false)
    private String complaint;

    @Column(name = "answer", nullable = false)
    private String answer;


    @Column(name = "status", nullable = false)
    private RequestStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "advertiser")
    private Advertiser advertiser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client")
    private Client client;

}
