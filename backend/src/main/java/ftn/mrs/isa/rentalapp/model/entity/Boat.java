package ftn.mrs.isa.rentalapp.model.entity;

import ftn.mrs.isa.rentalapp.model.user.BoatOwner;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "boats")
public class Boat extends EntityType{
    public Boat() {
    }

    public Boat(Integer id) {
        super();
        this.id = id;
    }

   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boatOwner")
    private BoatOwner boatOwner;

    @Column(name = "type", nullable = false)
    private BoatType type;

    @Column(name = "length", nullable = false)
    private Double length;

    @Column(name = "motorNum", nullable = false)
    private Integer MotorNum;

    @Column(name = "power", nullable = false)
    private Integer power;

    @Column(name = "maxSpeed", nullable = false)
    private Integer maxSpeed;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "cancelFee", nullable = false)
    private Integer cancelFee;

    @OneToMany(mappedBy = "boat", fetch = FetchType.LAZY)
    private Set<NavigationEquipment> navigationEquipment = new HashSet<NavigationEquipment>();

    @OneToMany(mappedBy = "boat", fetch = FetchType.LAZY)
    private Set<FishingEquipment> fishingEquipment = new HashSet<FishingEquipment>();

    /* CJENOVNIK */


}
