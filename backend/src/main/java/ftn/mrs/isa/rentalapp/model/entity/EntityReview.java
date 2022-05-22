package ftn.mrs.isa.rentalapp.model.entity;


import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import ftn.mrs.isa.rentalapp.model.user.Client;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "entityReviews")
public class EntityReview {
    @Id
    @SequenceGenerator(name = "mySeqGenV4", sequenceName = "mySeqV4", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV4")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "grade", nullable = false)
    private Integer grade;

    @Column(name = "review", nullable = false)
    private String review;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entity")
    private EntityType entity;

    @Column(name = "status", nullable = false)
    private RequestStatus status;
}
