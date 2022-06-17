package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.RankingInfoDTO;
import ftn.mrs.isa.rentalapp.dto.SystemInfoDTO;
import ftn.mrs.isa.rentalapp.model.entity.EntityReview;
import ftn.mrs.isa.rentalapp.model.reservation.RequestStatus;
import ftn.mrs.isa.rentalapp.model.system_info.RankingInfo;
import ftn.mrs.isa.rentalapp.model.system_info.SystemInfo;
import ftn.mrs.isa.rentalapp.service.RankingInfoService;
import ftn.mrs.isa.rentalapp.service.SystemInfoService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/system")
public class SystemController {

    @Autowired
    private RankingInfoService rankingInfoService;
    @Autowired
    private SystemInfoService systemInfoService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(value = "/getRankingInfo/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<RankingInfoDTO> getRankingInfo(@PathVariable Integer id, Principal principal){
        RankingInfo rankingInfo = rankingInfoService.findOne(id);
        if(rankingInfo == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        RankingInfoDTO infoDTO = mapper.map(rankingInfo, RankingInfoDTO.class);
        return new ResponseEntity<>(infoDTO,HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> saveRank(@RequestBody RankingInfoDTO rankDTO, Principal principal){
        RankingInfo info = mapper.map(rankDTO, RankingInfo.class);
        rankingInfoService.save(info);
        return new ResponseEntity<>("saved",HttpStatus.OK);
    }

    @PostMapping(value = "/saveSystemInfo")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> saveSystemInfo(@RequestBody SystemInfoDTO systemInfoDTO, Principal principal){
        SystemInfo info = mapper.map(systemInfoDTO, SystemInfo.class);
        systemInfoService.save(info);
        return new ResponseEntity<>("saved",HttpStatus.OK);
    }

    @GetMapping(value = "/getSystemInfo")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<SystemInfoDTO> getRankingInfo(Principal principal){
        SystemInfo systemInfo = systemInfoService.findById(1);
        if(systemInfo == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        SystemInfoDTO infoDTO = mapper.map(systemInfo, SystemInfoDTO.class);
        return new ResponseEntity<>(infoDTO,HttpStatus.OK);
    }


}
