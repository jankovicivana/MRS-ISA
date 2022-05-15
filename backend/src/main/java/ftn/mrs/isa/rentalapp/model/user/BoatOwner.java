package ftn.mrs.isa.rentalapp.model.user;

import ftn.mrs.isa.rentalapp.model.entity.Boat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "boatOwners")
public class BoatOwner extends Advertiser{

    public BoatOwner() {
    }


    @OneToMany(mappedBy = "boatOwner", fetch = FetchType.EAGER)
    private Set<Boat> boats = new HashSet<Boat>();

}
