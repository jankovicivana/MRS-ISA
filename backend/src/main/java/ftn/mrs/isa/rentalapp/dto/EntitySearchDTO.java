package ftn.mrs.isa.rentalapp.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EntitySearchDTO {

    private String name;

    private Integer price;

    private Integer people;

    private String city;

    private LocalDate startDate;

    private LocalTime startTime;

    private LocalDate endDate;

    private LocalTime endTime;

    private Integer rating;

}
