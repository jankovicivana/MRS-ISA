package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.entity.AvailablePeriod;
import ftn.mrs.isa.rentalapp.repository.AvailablePeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AvailablePeriodService {

    @Autowired
    private AvailablePeriodRepository availablePeriodRepository;


    public void add(AvailablePeriod availablePeriod) {
        availablePeriodRepository.save(availablePeriod);
    }

    public boolean isAvailable(Integer id, LocalDate startDate, LocalDate endDate){
        List<AvailablePeriod> periodList = availablePeriodRepository.getAvailable(id, startDate, endDate);
        System.out.println(periodList);
        return !periodList.isEmpty();
    }
}
