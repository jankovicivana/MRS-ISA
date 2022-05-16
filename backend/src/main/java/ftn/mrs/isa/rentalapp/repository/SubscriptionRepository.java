package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription,Integer> {

    @Query(value = "SELECT * FROM public.subscriptions WHERE client = :id", nativeQuery = true)
    public List<Subscription> findAllByClientId( @Param("id") Integer id);
}
