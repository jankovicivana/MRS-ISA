package ftn.mrs.isa.rentalapp.model.entity;

import ftn.mrs.isa.rentalapp.model.user.CottageOwner;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "cottages")
public class Cottage extends EntityType{

    public Cottage(Integer id) {
        super();
        this.id = id;
    }

    public Cottage() {
    }

    @Column(name = "maxNumPerson", nullable = false)
    private Integer maxNumPerson;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "cottageOwnerId")
    private CottageOwner cottageOwner;

    @OneToMany(mappedBy = "cottage", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Room> rooms = new HashSet<Room>();



}
