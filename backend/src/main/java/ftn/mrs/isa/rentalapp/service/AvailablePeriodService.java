package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.entity.AvailablePeriod;
import ftn.mrs.isa.rentalapp.repository.AvailablePeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvailablePeriodService {

    @Autowired
    private AvailablePeriodRepository availablePeriodRepository;


    public void add(AvailablePeriod availablePeriod) {
        availablePeriodRepository.save(availablePeriod);
    }
}
