package ftn.mrs.isa.rentalapp.model.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "navigationEquipment")
public class NavigationEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "equipment", nullable = false)
    private NavigationEquipmentType equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boat")
    private Boat boat;

}
