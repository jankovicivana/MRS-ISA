package ftn.mrs.isa.rentalapp.model.entity;


import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import ftn.mrs.isa.rentalapp.model.user.Client;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "entityComplaints")
public class EntityComplaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "complaint", nullable = false)
    private String complaint;

    @Column(name = "answer", nullable = false)
    private String answer;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entity")
    private EntityType entity;


    @Column(name = "status", nullable = false)
    private RequestStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client")
    private Client client;



}
