package ftn.mrs.isa.rentalapp.dto;


import ftn.mrs.isa.rentalapp.model.entity.*;
import ftn.mrs.isa.rentalapp.model.reservation.QuickReservation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class CottageDTO {

    private Integer id;

    private String name;

    private Double price;

    private AddressDTO address;

    private String description;

    private Set<ImageDTO> images;

    private String cottageOwnerName;

    private Set<RuleDTO> rules;

    private Set<AdditionalServiceDTO> additionalServices;

    private Set<RoomDTO> rooms = new HashSet<>();

    private Integer maxNumPerson;

    private Set<QuickReservationDTO> quickReservations;





}
