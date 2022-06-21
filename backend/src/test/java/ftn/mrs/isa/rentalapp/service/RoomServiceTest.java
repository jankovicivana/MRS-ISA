package ftn.mrs.isa.rentalapp.service;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import ftn.mrs.isa.rentalapp.dto.RoomDTO;
import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.model.entity.Room;
import ftn.mrs.isa.rentalapp.repository.RoomRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepositoryMock;

    @InjectMocks
    private RoomService roomService;

    @Mock
    private Cottage cottageMock;



    @Test
    @Transactional
    @Rollback(true)
    public void testCreateRoom(){
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setBedNumber(5);
        roomDTO.setEntityId(cottageMock.getId());
        Room r = new Room();
        r.setCottage(cottageMock);
        r.setBedNumber(5);
        Room room = roomService.createRoom(roomDTO,cottageMock);
        assertEquals(room.getBedNumber().toString(), "5");
        assertEquals(room.getCottage(),cottageMock);
    }

    @Test
    public void testFindOneRoom(){
        Room r = new Room();
        r.setCottage(cottageMock);
        r.setBedNumber(5);
        when(roomRepositoryMock.findById(4)).thenReturn(Optional.of(r));
        Room room = roomService.findOne(4);
        assertEquals(room,r);
        verify(roomRepositoryMock, times(1)).findById(4);
        verifyNoMoreInteractions(roomRepositoryMock);
    }

    @Test
    public void removeRoom(){
        Room r1 = new Room();
        r1.setCottage(cottageMock);
        r1.setBedNumber(5);
        Room r2 = new Room();
        r2.setCottage(cottageMock);
        r2.setBedNumber(3);

        when(roomRepositoryMock.findAll()).thenReturn(Arrays.asList(r1,r2));
        doNothing().when(roomRepositoryMock).deleteById(4);
        when(roomRepositoryMock.findById(4)).thenReturn(Optional.empty());

        int sizeBefore = roomService.findAllRooms().size();
        roomService.remove(4);

        when(roomRepositoryMock.findAll()).thenReturn(Arrays.asList(r2));

        List<Room> rooms = roomService.findAllRooms();
        assertThat(rooms).hasSize(sizeBefore-1);

        Room room = roomService.findOne(4);
        assertThat(room).isNull();


        verify(roomRepositoryMock, times(1)).deleteById(4);
        verify(roomRepositoryMock, times(2)).findAll();
        verify(roomRepositoryMock, times(1)).findById(4);
        verifyNoMoreInteractions(roomRepositoryMock);
    }

}
