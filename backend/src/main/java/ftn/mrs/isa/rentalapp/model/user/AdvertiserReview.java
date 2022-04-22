package ftn.mrs.isa.rentalapp.model.user;


import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "advertiserReviews")
public class AdvertiserReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "grade", nullable = false)
    private Integer grade;

    @Column(name = "review", nullable = false)
    private String review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertiser")
    private Advertiser advertiser;

}
