package ftn.mrs.isa.rentalapp.model.reservation;

import ftn.mrs.isa.rentalapp.model.entity.AdditionalService;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "quickReservations")
public class QuickReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "startDateTime", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "endDateTime", nullable = false)
    private LocalDateTime endDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity")
    private EntityType entity;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "deleted")
    private boolean deleted = false;

    @Column(name = "discountedPrice", nullable = false)
    private Double discountedPrice;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation", nullable = true)
    private Reservation reservation;


}
