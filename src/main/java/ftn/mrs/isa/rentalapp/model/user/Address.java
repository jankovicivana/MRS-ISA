package ftn.mrs.isa.rentalapp.model.user;

import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "addresses")
public class Address {

    public Address() {
    }

    public Address(Long id, String street, String city, Integer postalCode, String country) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<User>();

    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
    private Set<EntityType> entities = new HashSet<EntityType>();

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "postalCode", nullable = false)
    private Integer postalCode;

    @Column(name = "country", nullable = false)
    private String country;
}
