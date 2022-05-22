package ftn.mrs.isa.rentalapp.model.user;


import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "advertiserReviews")
public class AdvertiserReview {

    @Id
    @SequenceGenerator(name = "mySeqGenV3", sequenceName = "mySeqV3", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV3")
    private Integer id;

    @Column(name = "grade", nullable = false)
    private Integer grade;

    @Column(name = "review", nullable = false)
    private String review;

    @Column(name = "status", nullable = false)
    private RequestStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "advertiser")
    private Advertiser advertiser;

}
