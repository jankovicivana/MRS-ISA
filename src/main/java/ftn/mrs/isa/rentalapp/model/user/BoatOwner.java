package ftn.mrs.isa.rentalapp.model.user;

import ftn.mrs.isa.rentalapp.model.entity.Boat;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "boatOwners")
public class BoatOwner extends Advertiser{

    public BoatOwner() {
    }


    @OneToMany(mappedBy = "boatOwner", fetch = FetchType.LAZY)
    private Set<Boat> boats = new HashSet<Boat>();

}
