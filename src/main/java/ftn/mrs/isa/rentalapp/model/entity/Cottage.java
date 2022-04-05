package ftn.mrs.isa.rentalapp.model.entity;

import ftn.mrs.isa.rentalapp.model.user.CottageOwner;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/* getter i setter */

@Entity
@Table(name = "cottages")
public class Cottage extends EntityType{

    public Cottage(Integer id) {
        super();
        this.id = id;
    }

    public Cottage() {
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cottageOwnerId")
    private CottageOwner cottageOwner;

    @OneToMany(mappedBy = "cottage", fetch = FetchType.LAZY)
    private Set<Room> rooms = new HashSet<Room>();



}
