package ftn.mrs.isa.rentalapp.repository;

import ftn.mrs.isa.rentalapp.model.system_info.RankingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankingInfoRepository extends JpaRepository<RankingInfo,Integer> {
}
