package ftn.mrs.isa.rentalapp.model.entity;


import com.sun.org.apache.xpath.internal.operations.Bool;
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
    private Long id;

    @Column(name = "complaint", nullable = false)
    private String complaint;

    @Column(name = "answer", nullable = false)
    private String answer;

    @Column(name = "isAnswered", nullable = false)
    private Boolean isAnswered;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity")
    private EntityType entity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client")
    private Client client;


}
