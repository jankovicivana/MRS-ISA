package ftn.mrs.isa.rentalapp.dto;

import ftn.mrs.isa.rentalapp.model.entity.NavigationEquipmentType;
import lombok.Data;

@Data
public class NavigationEquipmentDTO {

    private Integer id;

    private String equipment;

    private Integer boatId;
}
