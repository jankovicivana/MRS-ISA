package ftn.mrs.isa.rentalapp.model.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "bedNumber", nullable = false)
    private Integer bedNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cottage")
    private Cottage cottage;

}
