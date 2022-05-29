package ftn.mrs.isa.rentalapp.model.user;


import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "accountDeleteRequests")
public class AccountDeleteRequest {
    @Id
    @SequenceGenerator(name = "mySeqGenV6", sequenceName = "mySeqV6", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV6")
    private Integer id;

    @Column(name = "requestReason")
    private String requestReason;

    @Column(name = "status", nullable = false)
    private RequestStatus status;

    @Column(name = "answer", nullable = true)
    private String answer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User userId;

}
