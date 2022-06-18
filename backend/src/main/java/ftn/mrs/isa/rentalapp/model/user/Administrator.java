package ftn.mrs.isa.rentalapp.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "admins")
public class Administrator extends User{

    public Administrator() {
    }

    @Column(name = "isPredefined", nullable = false)
    private Boolean isPredefined;

    @Column(name = "passwordChanged", nullable = false)
    private Boolean passwordChanged;

}
