package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.model.entity.Room;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoomService {

    public Set<Room> createRoomFromString(Set<String> roomsDTO, Cottage cottage){
        if (roomsDTO == null) {
            return null;
        }
        Set<Room> rooms = new HashSet<>();
        for (String roomDTO: roomsDTO) {
            Room r = new Room();
            r.setBedNumber(Integer.parseInt(roomDTO));
            r.setCottage(cottage);
            rooms.add(r);
        }
        return rooms;
    }
}
