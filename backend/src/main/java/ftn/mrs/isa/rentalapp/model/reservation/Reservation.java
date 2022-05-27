package ftn.mrs.isa.rentalapp.model.reservation;


import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import ftn.mrs.isa.rentalapp.model.user.Client;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "startDateTime", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "endDateTime", nullable = false)
    private LocalDateTime endDateTime;

    @Column(name = "deleted")
    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entity")
    private EntityType entity;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "systemProfit", nullable = false)
    private Double systemProfit;

    @Column(name = "advertiserProfit", nullable = false)
    private Double advertiserProfit;

    @Column(name = "personNum", nullable = false)
    private Integer personNum;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client", nullable = true)
    private Client client;

    @Column(name = "isCanceled", nullable = false)
    private Boolean isCanceled;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quickReservation", nullable = true)
    private QuickReservation quickReservation;

}
