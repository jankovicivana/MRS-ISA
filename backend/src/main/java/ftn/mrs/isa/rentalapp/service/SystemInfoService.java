package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.system_info.RankingInfo;
import ftn.mrs.isa.rentalapp.model.system_info.SystemInfo;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.model.user.User;
import ftn.mrs.isa.rentalapp.repository.SystemInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemInfoService {

    @Autowired
    private SystemInfoRepository systemInfoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RankingInfoService rankingInfoService;

    @Autowired
    private EntityService entityService;


    public SystemInfo findById(int i) {
        return systemInfoRepository.findById(i).orElse(null);
    }

    public void save(SystemInfo info) {
        systemInfoRepository.save(info);
    }

    public double calculateSystemProfit(int entityId, double price, Client client){
        User owner = userService.findUserByEntity(entityService.findOne(entityId));
        RankingInfo advertiserRank = rankingInfoService.findRank(owner.getPoints());
        SystemInfo systemInfo = findById(1);
        double advertiserProfit = price - price*systemInfo.getSystemProfit()/100;
        advertiserProfit = advertiserProfit + advertiserProfit * advertiserRank.getAdvertiserBenefits() / 100;
        double systemProfit = price - advertiserProfit;
        userService.setPoints(client,client.getPoints()+systemInfo.getClientPoints());
        userService.setPoints(owner,owner.getPoints()+systemInfo.getAdvertiserPoints());
        return systemProfit;

    }
}
