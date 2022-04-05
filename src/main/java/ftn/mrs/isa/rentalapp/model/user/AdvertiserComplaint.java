package ftn.mrs.isa.rentalapp.model.user;


import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@Getter
@Setter
@Entity
@Table(name = "advertiserComplaints")
public class AdvertiserComplaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "complaint", nullable = false)
    private String complaint;

    @Column(name = "answer", nullable = false)
    private String answer;

    @Column(name = "isAnswered", nullable = false)
    private Boolean isAnswered;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertiser")
    private Advertiser advertiser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client")
    private Client client;

}
