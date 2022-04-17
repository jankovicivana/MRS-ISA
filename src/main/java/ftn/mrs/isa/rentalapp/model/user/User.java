package ftn.mrs.isa.rentalapp.model.user;

import ftn.mrs.isa.rentalapp.model.system_info.Email;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@Getter
@Setter
@Entity
@Inheritance(strategy=TABLE_PER_CLASS)

public abstract class User {

    public User() {
    }

    @Id
    @SequenceGenerator(name = "mySeqGenV1", sequenceName = "mySeqV1", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV1")
    protected Long id;

    @Column(name = "email", unique = true, nullable = false)
    protected String email;

    @Column(name = "password", nullable = false)
    protected String password;

    @Column(name = "name", nullable = false)
    protected String name;

    @Column(name = "surname", nullable = false)
    protected String surname;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "address")
    protected Address address;

    @Column(name = "phoneNumber", nullable = false)
    protected String phoneNumber;

    @Column(name = "type", nullable = false)
    protected UserType type;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "accountDeleteRequest", nullable = true)
    protected AccountDeleteRequest accountDeleteRequest;

    @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
    protected Set<Email> emails = new HashSet<Email>();
}
