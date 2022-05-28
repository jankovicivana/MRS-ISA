package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.reservation.Report;
import ftn.mrs.isa.rentalapp.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public void save(Report report){ reportRepository.save(report);}

    public List<Report> getReportsOnHold() {
        return  reportRepository.getReportsOnHold();
    }

    public Report findOne(Integer id) {
        return  reportRepository.findById(id).orElse(null);
    }
}
