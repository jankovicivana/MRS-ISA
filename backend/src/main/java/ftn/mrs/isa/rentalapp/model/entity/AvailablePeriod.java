package ftn.mrs.isa.rentalapp.model.entity;


import ftn.mrs.isa.rentalapp.model.user.FishingInstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "availablePeriods")
public class AvailablePeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "startDateTime", nullable = false)
    private LocalDate startDateTime;//stavi i time

    @Column(name = "endDateTime", nullable = false)
    private LocalDate endDateTime; //stavi i time

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity")
    private EntityType entity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fishingInstructor")
    private FishingInstructor fishingInstructor;

}
