package ftn.mrs.isa.rentalapp.model.entity;

import ftn.mrs.isa.rentalapp.model.user.BoatOwner;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "boats")
public class Boat extends EntityType{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "boatOwner")
    private BoatOwner boatOwner;

    @Column(name = "type", nullable = false)
    private BoatType type;

    @Column(name = "length", nullable = false)
    private Double length;

    @Column(name = "motorNum", nullable = false)
    private Integer motorNum;

    @Column(name = "power", nullable = false)
    private Integer power;

    @Column(name = "maxSpeed", nullable = false)
    private Integer maxSpeed;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "cancelFee", nullable = false)
    private Integer cancelFee;

    @OneToMany(mappedBy = "boat", fetch = FetchType.EAGER)
    private Set<NavigationEquipment> navigationEquipment = new HashSet<NavigationEquipment>();

    @OneToMany(mappedBy = "entity", fetch = FetchType.EAGER)
    private Set<FishingEquipment> fishingEquipment = new HashSet<FishingEquipment>();

    /* CJENOVNIK */


}
