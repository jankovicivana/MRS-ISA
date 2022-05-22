package ftn.mrs.isa.rentalapp.model.user;

import ftn.mrs.isa.rentalapp.model.entity.Cottage;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cottageOwners")
public class CottageOwner extends Advertiser{

    public CottageOwner() {
    }


    @OneToMany(mappedBy = "cottageOwner", fetch = FetchType.LAZY)
    private Set<Cottage> cottages = new HashSet<Cottage>();

}
