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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "requestReason")
    private String requestReason;

    @Column(name = "status", nullable = false)
    private RequestStatus status;

    @Column(name = "answer", nullable = true)
    private String answer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User userId;

}
