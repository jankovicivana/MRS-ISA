package ftn.mrs.isa.rentalapp.model.user;


import ftn.mrs.isa.rentalapp.model.reservation.Report;
import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@Getter
@Setter
@Entity
public abstract class Advertiser extends User{

    public Advertiser() {
    }

    @Column(name = "points")
    protected Integer points;

    @Column(name = "registrationStatus", nullable = false)
    protected RequestStatus registrationStatus;

    @OneToMany(mappedBy = "advertiser", fetch = FetchType.LAZY)
    protected Set<AdvertiserReview> reviews = new HashSet<AdvertiserReview>();

    @OneToMany(mappedBy = "advertiser", fetch = FetchType.LAZY)
    protected Set<Report> reports = new HashSet<Report>();

    @OneToMany(mappedBy = "advertiser", fetch = FetchType.LAZY)
    protected Set<AdvertiserComplaint> complaints = new HashSet<AdvertiserComplaint>();

}
