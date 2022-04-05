package ftn.mrs.isa.rentalapp.model.reservation;

import ftn.mrs.isa.rentalapp.model.entity.AdditionalService;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import ftn.mrs.isa.rentalapp.model.user.Client;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "quickReservations")
public class QuickReservation {

    public QuickReservation(Long id, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.startDateTime = startDate;
        this.endDateTime = endDate;
    }

    public QuickReservation() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "startDateTime", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "endDateTime", nullable = false)
    private LocalDateTime endDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity")
    private EntityType entity;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "maxPersonNum", nullable = false)
    private Integer maxPersonNum;

    @OneToMany(mappedBy = "quickReservation", fetch = FetchType.LAZY)
    private Set<AdditionalService> additionalServices = new HashSet<AdditionalService>();

   /* @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client", nullable = true)
    private Client client;*/

    @Column(name = "isReserved", nullable = false)
    private Boolean isReserved;

    @Column(name = "discount", nullable = false)
    private Integer discount;

    @Column(name = "expirationDateTime", nullable = false)
    private LocalDateTime expirationDateTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation", nullable = true)
    private Reservation reservation;


}
