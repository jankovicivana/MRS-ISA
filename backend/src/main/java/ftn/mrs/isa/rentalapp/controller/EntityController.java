package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.CottageDTO;
import ftn.mrs.isa.rentalapp.dto.CottageOwnerDTO;
import ftn.mrs.isa.rentalapp.dto.EntitySearchDTO;
import ftn.mrs.isa.rentalapp.dto.EntityTypeDTO;
import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.model.entity.EntityKind;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import ftn.mrs.isa.rentalapp.model.user.CottageOwner;
import ftn.mrs.isa.rentalapp.service.AvailablePeriodService;
import ftn.mrs.isa.rentalapp.service.CottageService;
import ftn.mrs.isa.rentalapp.service.EntityService;
import ftn.mrs.isa.rentalapp.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/entity")
public class EntityController {

    @Autowired
    private EntityService entityService;

    @Autowired
    private CottageService cottageService;

    @Autowired
    private AvailablePeriodService availablePeriodService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping(value = "/getAvailable")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<List<EntityTypeDTO>> getAvailable(@RequestBody EntitySearchDTO params){
        List<EntityType> entities;
        List<EntityTypeDTO> entitiesDTO = new ArrayList<>();
        LocalDateTime start = LocalDateTime.of(params.getStartDate(), params.getStartTime());
        LocalDateTime end = LocalDateTime.of(params.getEndDate(), params.getEndTime());

        /*entities = entityService.getByParams(params);
        for(EntityType e: entities){
            if(availablePeriodService.isAvailable(e.getId(), params.getStartDate(), params.getEndDate()) && !reservationService.isReserved(e.getId(), start, end)){
                entitiesDTO.add(mapper.map(e, EntityTypeDTO.class));
            }
        }*/

        entities = entityService.getAll();
        for(EntityType et: entities){
            if (et.getPrice() < params.getPrice() && et.getAddress().getCity().equals(params.getCity())){
                if(availablePeriodService.isAvailable(et.getId(), params.getStartDate(), params.getEndDate()) && !reservationService.isReserved(et.getId(), start, end)){
                    EntityKind kind = et.getKind();
                    EntityTypeDTO dto = mapper.map(et, EntityTypeDTO.class);
                    dto.setType(EntityKind.toString(kind));
                    entitiesDTO.add(dto);
                }
            }
        }

        return new ResponseEntity<>(entitiesDTO, HttpStatus.OK);
    }

}
