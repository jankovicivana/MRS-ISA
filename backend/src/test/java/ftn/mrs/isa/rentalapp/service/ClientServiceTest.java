package ftn.mrs.isa.rentalapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import ftn.mrs.isa.rentalapp.dto.UserRequest;
import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.model.user.Role;
import ftn.mrs.isa.rentalapp.repository.ClientRepository;
import ftn.mrs.isa.rentalapp.repository.ReservationRepository;
import ftn.mrs.isa.rentalapp.repository.RoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private Client clientMock;

    @Mock
    private UserRequest userRequestMock;


    @Test
    @Transactional
    public void testDelete(){
        Client c = new Client();
        c.setId(2);
        c.setName("ivana");
        c.setSurname("jankovic");
        c.setAddress(new Address("Republike Srpske 17", "Trebinje", 81100, "BiH"));
        c.setEmail("ivanaj0610@gmail.com");
        c.setDeleted(false);
        c.setEnabled(true);
        c.setPenalties(1);
        c.setPoints(0);
        c.setPhoneNumber("065");

        when(clientRepository.findById(2)).thenReturn(Optional.of(c));
        when(reservationRepository.getReservationByClient(LocalDate.now(), 2)).thenReturn(new ArrayList<>());
        clientService.delete(2);
        c.setDeleted(true);
        when(clientRepository.findAll()).thenReturn(Arrays.asList(c));
        List<Client> clients = clientRepository.findAll();
        assertEquals(clients.get(0).getName(), "ivana");
        assertTrue(clients.get(0).isDeleted());

        verify(clientRepository, times(1)).findById(2);
        verify(clientRepository, times(1)).findAll();
        verify(clientRepository, times(1)).save(c);
        verifyNoMoreInteractions(clientRepository);
    }


    @Test
    @Transactional
    public void testSave(){
        Client c = new Client();
        c.setName("ivana");
        c.setSurname("jankovic");
        c.setPassword("pass");
        c.setAddress(new Address("Republike Srpske 17", "Trebinje", 81100, "BiH"));
        c.setEmail("ivanaj0610+v@gmail.com");
        c.setDeleted(false);
        c.setEnabled(true);
        c.setPenalties(0);
        c.setPoints(0);
        c.setPhoneNumber("065");

        UserRequest u = new UserRequest();
        u.setName("ivana");
        u.setSurname("jankovic");
        u.setStreet("Republike Srpske 17");
        u.setCity("Trebinje");
        u.setCountry("BiH");
        u.setPostalCode(81100);
        u.setEmail("ivanaj0610+v@gmail.com");
        u.setPhoneNumber("065");
        u.setPassword("pass");
        u.setRole("ROLE_client");
        u.setReason("");
        u.setBiography("");

        Role r = new Role();
        r.setId(1L);
        r.setName("ROLE_client");
        List<Role> roles = new ArrayList<Role>();
        roles.add(r);

        c.setRoles(roles);

       // when(roleRepository.findByName("ROLE_client")).thenReturn(roles);
        when(clientRepository.save(c)).thenReturn(c);
        clientService.save(c);

        when(clientRepository.findByEmail("ivanaj0610+v@gmail.com")).thenReturn(c);
        Client newClient = clientRepository.findByEmail("ivanaj0610+v@gmail.com");

        assertEquals(newClient.getName(), c.getName());

       // verify(roleRepository, times(1)).findByName("ROLE_client");
        //verifyNoMoreInteractions(roleRepository);
        verify(clientRepository, times(1)).save(c);
        verify(clientRepository, times(1)).findByEmail("ivanaj0610+v@gmail.com");

        verifyNoMoreInteractions(clientRepository);

    }


    @Test
    @Transactional
    public void testFindAllClients(){
        Client c = new Client();
        c.setName("ivana");
        c.setSurname("jankovic");
        c.setAddress(new Address("Republike Srpske 17", "Trebinje", 81100, "BiH"));
        c.setEmail("ivanaj0610+v@gmail.com");
        c.setDeleted(false);
        c.setEnabled(true);
        c.setPenalties(0);
        c.setPoints(0);
        c.setPhoneNumber("065");

        when(clientRepository.findAll()).thenReturn(Arrays.asList(c));
        List<Client> clients = clientService.findAllClients();

        assertThat(clients).hasSize(1);
        assertEquals(clients.get(0).getName(), "ivana");

        verify(clientRepository, times(1)).findAll();
        verifyNoMoreInteractions(clientRepository);
    }

}
