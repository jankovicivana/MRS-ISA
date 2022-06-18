package ftn.mrs.isa.rentalapp.model.user;

import ftn.mrs.isa.rentalapp.model.entity.EntityComplaint;
import ftn.mrs.isa.rentalapp.model.entity.EntityReview;
import ftn.mrs.isa.rentalapp.model.entity.Subscription;
import ftn.mrs.isa.rentalapp.model.reservation.Report;
import ftn.mrs.isa.rentalapp.model.reservation.Reservation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "clients")
public class Client extends User{

    public Client() {
    }

   /* @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private Set<QuickReservation> quickReservations = new HashSet<QuickReservation>();
*/
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private Set<AdvertiserReview> advertiserReviews = new HashSet<AdvertiserReview>();

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private Set<EntityReview> entityReviews = new HashSet<EntityReview>();

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private Set<EntityComplaint> entityComplaints = new HashSet<EntityComplaint>();

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private Set<AdvertiserComplaint> advertiserComplaints = new HashSet<AdvertiserComplaint>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Reservation> reservations = new HashSet<Reservation>();

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private Set<Report> reports = new HashSet<Report>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Subscription> subscriptions = new HashSet<Subscription>();


    @Column(name = "penalties", nullable = false)
    private Integer penalties;
}
