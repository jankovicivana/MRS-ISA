package ftn.mrs.isa.rentalapp.model.user;

import ftn.mrs.isa.rentalapp.model.entity.Adventure;
import ftn.mrs.isa.rentalapp.model.entity.AvailablePeriod;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "fishingInstructors")
public class FishingInstructor extends Advertiser{

    public FishingInstructor() {
    }

    @OneToMany(mappedBy = "fishingInstructor", fetch = FetchType.EAGER)
    private Set<Adventure> adventures = new HashSet<Adventure>();

    @Column(name = "biography", nullable = false)
    private String biography;

    @OneToMany(mappedBy = "fishingInstructor", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<AvailablePeriod> availablePeriods = new HashSet<AvailablePeriod>();

}
