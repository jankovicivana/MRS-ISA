package ftn.mrs.isa.rentalapp.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "rules")
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "rule", nullable = false)
    private String rule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity")
    private EntityType entity;


}
