package ftn.mrs.isa.rentalapp.model.user;

import ftn.mrs.isa.rentalapp.model.system_info.Email;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.*;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@Getter
@Setter
@Entity
@Table(name = "users")
@Inheritance(strategy=TABLE_PER_CLASS)
public class User implements UserDetails {

    public User() {
    }

    @Id
    @SequenceGenerator(name = "mySeqGenV1", sequenceName = "mySeqV1", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV1")
    protected Integer id;

    @Column(name = "email", unique = true, nullable = false)
    protected String email;

    @Column(name = "password", nullable = false)
    protected String password;

    @Column(name = "name", nullable = false)
    protected String name;

    @Column(name = "deleted")
    private boolean deleted = false;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "surname", nullable = false)
    protected String surname;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address")
    protected Address address;

    @Column(name = "phoneNumber", nullable = false)
    protected String phoneNumber;

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

    @Column(name = "type", nullable = false)
    protected String type;

    @Column(name = "mainPhoto", nullable = true)
    protected String mainPhoto;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "accountDeleteRequest", nullable = true)
    protected AccountDeleteRequest accountDeleteRequest;

    @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
    protected Set<Email> emails = new HashSet<Email>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
