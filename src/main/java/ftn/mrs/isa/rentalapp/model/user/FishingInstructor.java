package ftn.mrs.isa.rentalapp.model.user;

import ftn.mrs.isa.rentalapp.model.entity.Adventure;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fishingInstructors")
public class FishingInstructor extends Advertiser{

    public FishingInstructor() {
    }

    @OneToMany(mappedBy = "fishingInstructor", fetch = FetchType.LAZY)
    private Set<Adventure> adventures = new HashSet<Adventure>();

    @Column(name = "biography", nullable = false)
    private String biography;

}
