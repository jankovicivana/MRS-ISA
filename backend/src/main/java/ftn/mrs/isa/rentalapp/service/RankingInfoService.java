package ftn.mrs.isa.rentalapp.service;


import ftn.mrs.isa.rentalapp.model.system_info.RankingInfo;
import ftn.mrs.isa.rentalapp.repository.RankingInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankingInfoService {

    @Autowired
    private RankingInfoRepository rankingInfoRepository;

    public RankingInfo findOne(Integer id){return rankingInfoRepository.findById(id).orElse(null);}

    public void save(RankingInfo info) {
        rankingInfoRepository.save(info);
    }
}
