package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.CottageDTO;
import ftn.mrs.isa.rentalapp.dto.CottageOwnerDTO;
import ftn.mrs.isa.rentalapp.dto.EntitySearchDTO;
import ftn.mrs.isa.rentalapp.dto.EntityTypeDTO;
import ftn.mrs.isa.rentalapp.model.entity.*;
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

        entities = entityService.getAll();
        for(EntityType et: entities){
            if (EntityKind.toString(et.getKind()).equals(params.getType()) && et.getPrice() <= params.getPrice()
                    && et.getAddress().getCity().equals(params.getCity()) && et.getAverageGrade() > params.getRating()){
                boolean isAvailable = false;
                if(et.getKind() == EntityKind.ADVENTURE){
                    Adventure a = (Adventure)et;
                    if(a.getMaxPersonNum() < params.getPeople()){
                        continue;
                    }
                    isAvailable = availablePeriodService.isAvailableInstructor(a.getFishingInstructor().getId(), start, end);
                } else{
                    if(et.getKind() == EntityKind.COTTAGE) {
                        Cottage c = (Cottage)et;
                        if(c.getMaxNumPerson() < params.getPeople()){
                            continue;
                        }
                    } else{
                        Boat b = (Boat) et;
                        if(b.getCapacity() < params.getPeople()){
                            continue;
                        }
                    }
                    isAvailable = availablePeriodService.isAvailable(et.getId(), start, end);
                }
                if(isAvailable && !reservationService.isReserved(et.getId(), start, end)){
                    EntityKind kind = et.getKind();
                    EntityTypeDTO dto = mapper.map(et, EntityTypeDTO.class);
                    dto.setType(EntityKind.toString(kind));
                    entitiesDTO.add(dto);
                }
            }
        }
        System.out.println(entitiesDTO.size());
        return new ResponseEntity<>(entitiesDTO, HttpStatus.OK);
    }

}
