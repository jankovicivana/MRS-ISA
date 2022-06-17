package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.system_info.SystemInfo;
import ftn.mrs.isa.rentalapp.repository.SystemInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemInfoService {

    @Autowired
    private SystemInfoRepository systemInfoRepository;


    public SystemInfo findById(int i) {
        return systemInfoRepository.findById(i).orElse(null);
    }

    public void save(SystemInfo info) {
        systemInfoRepository.save(info);
    }
}
