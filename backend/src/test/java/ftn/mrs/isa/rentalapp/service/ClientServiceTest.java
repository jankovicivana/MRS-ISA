package ftn.mrs.isa.rentalapp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import ftn.mrs.isa.rentalapp.dto.UserRequest;
import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.model.user.Role;
import ftn.mrs.isa.rentalapp.repository.ClientRepository;
import ftn.mrs.isa.rentalapp.repository.RoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientService clientService;

    @Mock
    private RoleRepository roleRepository;


    @Test
    @Transactional
    @Rollback(true)
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
        when(clientService.findOne(2)).thenReturn(c);
        when(clientService.canDeleteClient(c)).thenReturn(true);

        clientService.delete(2);

        Optional<Client> client = clientRepository.findById(2);
        assertEquals(client.get().getName(), "ivana");
        assertFalse(client.get().isDeleted());

        verify(clientService, times(1)).delete(2);
        verifyNoMoreInteractions(clientService);
    }


    @Test
    @Transactional
    public void testSave(){
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

        when(roleRepository.findByName("ROLE_client")).thenReturn(roles);
        when(clientRepository.save(c)).thenReturn(c);
        when(clientService.save(u)).thenReturn(c);
        clientService.save(u);
        when(clientRepository.findByEmail("ivanaj0610+v@gmail.com")).thenReturn(c);

        Client client = clientRepository.findByEmail("ivanaj0610+v@gmail.com");
        assertEquals(client.getName(), "ivana");
        verify(clientService, times(1)).save(u);
        verifyNoMoreInteractions(clientService);
    }

}
