package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.model.entity.Room;
import ftn.mrs.isa.rentalapp.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public void save(Room room){roomRepository.save(room);}

    public Room findOne(Integer id){return roomRepository.findById(id).orElseGet(null);}

    public void remove(Integer id){ roomRepository.deleteById(id);}

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
            //save(r);
        }
        return rooms;
    }
}
