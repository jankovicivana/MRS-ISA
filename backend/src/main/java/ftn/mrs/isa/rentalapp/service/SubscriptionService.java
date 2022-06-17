package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.entity.Subscription;
import ftn.mrs.isa.rentalapp.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public List<Subscription> getSubscriptionsByUser(Integer id) {
        return subscriptionRepository.findAllByClientId(id);
    }

    public Subscription findOne(Integer id) {
        return subscriptionRepository.findById(id).orElse(null);
    }

    public void deleteSubscription(Subscription sub) {
        subscriptionRepository.delete(sub);
    }

    public void save(Subscription s){ subscriptionRepository.save(s); }
}
