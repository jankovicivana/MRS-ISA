package ftn.mrs.isa.rentalapp.model.entity;

import ftn.mrs.isa.rentalapp.model.user.FishingInstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "adventures")
public class Adventure extends EntityType{



 /*   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
*/

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fishingInstructorId")
    private FishingInstructor fishingInstructor;

    @OneToMany(mappedBy = "adventure", fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    private Set<FishingEquipment> fishingEquipment = new HashSet<FishingEquipment>();

    @Column(name = "cancelFee", nullable = false)
    private Integer cancelFee;

    @Column(name = "maxPersonNum", nullable = false)
    private Integer maxPersonNum;

}
