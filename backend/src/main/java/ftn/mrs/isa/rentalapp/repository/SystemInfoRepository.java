package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.system_info.SystemInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemInfoRepository extends JpaRepository<SystemInfo,Integer> {
}
