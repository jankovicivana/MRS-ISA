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

    public Reservation(){
    }

    public Reservation(LocalDateTime startDateTime, LocalDateTime endDateTime,
                       EntityType entity, Double price, Double systemProfit, Double advertiserProfit,
                       Integer personNum, Client client, QuickReservation quickReservation) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.deleted = false;
        this.entity = entity;
        this.price = price;
        this.systemProfit = systemProfit;
        this.advertiserProfit = advertiserProfit;
        this.personNum = personNum;
        this.client = client;
        this.isCanceled = false;
        this.quickReservation = quickReservation;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quickReservation", nullable = true)
    private QuickReservation quickReservation;

    @Column(name="isReviewed", nullable = true) // bice false
    private Boolean isReviewed = false;

    @Column(name="isComplained", nullable = true) // bice false
    private Boolean isComplained = false;

}
