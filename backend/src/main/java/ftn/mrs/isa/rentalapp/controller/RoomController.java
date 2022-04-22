package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.RoomDTO;
import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.model.entity.Room;
import ftn.mrs.isa.rentalapp.service.CottageService;
import ftn.mrs.isa.rentalapp.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/rooms")
public class RoomController {
    @Autowired
    private CottageService cottageService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/addRoom")
    public ResponseEntity<RoomDTO> addRoom(@RequestBody RoomDTO roomDTO){
        if(roomDTO.getEntityId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Cottage cottage = cottageService.findOne(roomDTO.getEntityId());

        if(cottage == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Room room = new Room();
        room.setBedNumber(roomDTO.getBedNumber());
        room.setCottage(cottage);
        cottage.getRooms().add(room);

        roomService.save(room);
        return new ResponseEntity<>(mapper.map(room, RoomDTO.class),HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/deleteRoom/{id}")
    public  ResponseEntity<Void> deleteRoom(@PathVariable Integer id){
        Room room = roomService.findOne(id);

        if(room != null){
            roomService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
