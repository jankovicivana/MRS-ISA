package ftn.mrs.isa.rentalapp.model.user;


import ftn.mrs.isa.rentalapp.model.reservation.Report;
import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "registrationReason", nullable = true)
    protected String registrationReason;

    @Column(name = "averageGrade")
    private Double averageGrade = 0.0;

    @OneToMany(mappedBy = "advertiser", fetch = FetchType.LAZY)
    protected Set<AdvertiserReview> reviews = new HashSet<AdvertiserReview>();

    @OneToMany(mappedBy = "advertiser", fetch = FetchType.LAZY)
    protected Set<Report> reports = new HashSet<Report>();

    @OneToMany(mappedBy = "advertiser", fetch = FetchType.LAZY)
    protected Set<AdvertiserComplaint> complaints = new HashSet<AdvertiserComplaint>();

}
