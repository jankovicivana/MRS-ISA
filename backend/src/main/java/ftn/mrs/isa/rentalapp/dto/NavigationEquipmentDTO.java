package ftn.mrs.isa.rentalapp.dto;

import ftn.mrs.isa.rentalapp.model.entity.NavigationEquipmentType;
import lombok.Data;

@Data
public class NavigationEquipmentDTO {

    private Long id;

    private NavigationEquipmentType equipment;

    private Integer boatId;
}
