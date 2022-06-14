package ftn.mrs.isa.rentalapp.service;


import ftn.mrs.isa.rentalapp.model.entity.EntityComplaint;
import ftn.mrs.isa.rentalapp.model.user.AdvertiserComplaint;
import ftn.mrs.isa.rentalapp.model.user.AdvertiserReview;
import ftn.mrs.isa.rentalapp.repository.AdvertiserComplaintRepository;
import ftn.mrs.isa.rentalapp.repository.EntityComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {

    @Autowired
    private AdvertiserComplaintRepository advertiserComplaintRepository;

    @Autowired
    private EntityComplaintRepository entityComplaintRepository;

    public List<AdvertiserComplaint> getOnHoldAdvertiserComplaints() {
        return advertiserComplaintRepository.findHoldOnComplaints();
    }

    public List<EntityComplaint> getOnHoldEntityComplaints() {
        return entityComplaintRepository.findHoldOnComplaints();

    }

    public AdvertiserComplaint findAdvertiserComplaint(Integer id) {
        return  advertiserComplaintRepository.findById(id).orElse(null);
    }

    public void saveAdvertiserComplaint(AdvertiserComplaint advertiserComplaint) {
        advertiserComplaintRepository.save(advertiserComplaint);
    }

    public EntityComplaint findEntityComplaint(Integer id) {
        return entityComplaintRepository.findById(id).orElse(null);
    }

    public void saveEntityComplaint(EntityComplaint entityComplaint) {
        entityComplaintRepository.save(entityComplaint);
    }
}
