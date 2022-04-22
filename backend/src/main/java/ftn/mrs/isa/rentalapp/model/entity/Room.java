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
    private Integer id;

    @Column(name = "bedNumber", nullable = false)
    private Integer bedNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cottage")
    private Cottage cottage;

    public Room(Integer bedNumber){
        this.bedNumber = bedNumber;
    }

    public Room() {

    }
}
