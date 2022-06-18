package ftn.mrs.isa.rentalapp.service;


import ftn.mrs.isa.rentalapp.dto.AdvertiserComplaintDTO;
import ftn.mrs.isa.rentalapp.dto.EntityComplaintDTO;
import ftn.mrs.isa.rentalapp.model.entity.EntityComplaint;
import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import ftn.mrs.isa.rentalapp.model.user.AdvertiserComplaint;
import ftn.mrs.isa.rentalapp.model.user.User;
import ftn.mrs.isa.rentalapp.repository.AdvertiserComplaintRepository;
import ftn.mrs.isa.rentalapp.repository.EntityComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.List;

@Transactional
@Service
public class ComplaintService {

    @Autowired
    private AdvertiserComplaintRepository advertiserComplaintRepository;

    @Autowired
    private EntityComplaintRepository entityComplaintRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

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

    public void saveEntityComplaint(EntityComplaint entityComplaint) {
        entityComplaintRepository.save(entityComplaint);
    }

    public boolean answerAdvertiserComplaint(AdvertiserComplaintDTO dto, RequestStatus status) throws MessagingException {
        AdvertiserComplaint advertiserComplaint = advertiserComplaintRepository.getAdvertiserComplaint(dto.getId());
        if(advertiserComplaint.getStatus()!=RequestStatus.ON_HOLD){
            return false;
        }
        advertiserComplaint.setAnswer(dto.getAnswer());
        advertiserComplaint.setStatus(status);
        saveAdvertiserComplaint(advertiserComplaint);
        if (status == RequestStatus.ACCEPTED) {
            emailService.sendComplaint(advertiserComplaint.getClient(), advertiserComplaint.getComplaint(), advertiserComplaint.getAnswer(), true);
            emailService.sendComplaint(advertiserComplaint.getAdvertiser(), advertiserComplaint.getComplaint(), advertiserComplaint.getAnswer(), true);
        }else{
            emailService.sendComplaint(advertiserComplaint.getClient(), advertiserComplaint.getComplaint(), advertiserComplaint.getAnswer(), false);
            emailService.sendComplaint(advertiserComplaint.getAdvertiser(), advertiserComplaint.getComplaint(), advertiserComplaint.getAnswer(), false);

        }
        return true;
    }

    public boolean answerEntityComplaint(EntityComplaintDTO dto, RequestStatus b) throws MessagingException {
        EntityComplaint entityComplaint = entityComplaintRepository.getEntityComplaint(dto.getId());
        if(entityComplaint.getStatus()!=RequestStatus.ON_HOLD){
            return false;
        }
        entityComplaint.setAnswer(dto.getAnswer());
        entityComplaint.setStatus(b);
        saveEntityComplaint(entityComplaint);
        User u = userService.findUserByEntity(entityComplaint.getEntity());
        if (b == RequestStatus.ACCEPTED) {
            emailService.sendComplaint(entityComplaint.getClient(), entityComplaint.getComplaint(), entityComplaint.getAnswer(), true);
            emailService.sendComplaint(u, entityComplaint.getComplaint(), entityComplaint.getAnswer(), true);
        }else{
            emailService.sendComplaint(entityComplaint.getClient(), entityComplaint.getComplaint(), entityComplaint.getAnswer(), false);
            emailService.sendComplaint(u, entityComplaint.getComplaint(), entityComplaint.getAnswer(), false);

        }
        return true;
    }
}
