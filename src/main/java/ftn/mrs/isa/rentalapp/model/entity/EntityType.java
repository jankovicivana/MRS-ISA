package ftn.mrs.isa.rentalapp.model.entity;


import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import ftn.mrs.isa.rentalapp.model.user.Address;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@Getter
@Setter
@Entity
@Inheritance(strategy=TABLE_PER_CLASS)
public abstract class EntityType {

    public EntityType(Integer id) {
        this.id = id;
    }

    public EntityType() {
    }

    @Id
    @SequenceGenerator(name = "mySeqGenV2", sequenceName = "mySeqV2", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV2")
    protected Integer id;

    @OneToMany(mappedBy = "entity", fetch = FetchType.LAZY)
    protected Set<QuickReservation> quickReservations = new HashSet<QuickReservation>();

    @OneToMany(mappedBy = "entity", fetch = FetchType.LAZY)
    protected Set<Reservation> reservations = new HashSet<Reservation>();

    @Column(name = "name", nullable = false)
    protected String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address")
    protected Address address;

    @Column(name = "price", nullable = false)
    protected Double price;

    @OneToMany(mappedBy = "entity", fetch = FetchType.LAZY)
    protected Set<Rule> rules = new HashSet<Rule>();

    @OneToMany(mappedBy = "entity", fetch = FetchType.LAZY)
    protected Set<AdditionalService> additionalServices = new HashSet<AdditionalService>();

    @OneToMany(mappedBy = "entity", fetch = FetchType.LAZY)
    protected Set<Image> images = new HashSet<Image>();

    @Column(name = "description", nullable = false)
    protected String description;

    @OneToMany(mappedBy = "entity", fetch = FetchType.LAZY)
    protected Set<EntityReview> reviews = new HashSet<EntityReview>();

    @OneToMany(mappedBy = "entity", fetch = FetchType.LAZY)
    protected Set<EntityComplaint> complaints = new HashSet<EntityComplaint>();

    @OneToMany(mappedBy = "entity", fetch = FetchType.LAZY)
    protected Set<AvailablePeriod> availablePeriods = new HashSet<AvailablePeriod>();

    @OneToMany(mappedBy = "entity")
    private Set<Subscription> subscriptions = new HashSet<Subscription>();

}
