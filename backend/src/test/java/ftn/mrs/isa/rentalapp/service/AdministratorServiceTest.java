package ftn.mrs.isa.rentalapp.service;


import ftn.mrs.isa.rentalapp.dto.AddressDTO;
import ftn.mrs.isa.rentalapp.dto.AdministratorCreateDTO;
import ftn.mrs.isa.rentalapp.dto.AdministratorDTO;
import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.model.user.Administrator;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.model.user.UserType;
import ftn.mrs.isa.rentalapp.repository.AdministratorRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdministratorServiceTest {

    @Mock
    private AdministratorRepository administratorRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private  AdministratorService administratorService;

    @Test
    @Transactional
    public void testCreateAdmin(){
        AdministratorCreateDTO  administratorCreateDTO = new AdministratorCreateDTO();
        administratorCreateDTO.setName("ivana");
        administratorCreateDTO.setSurname("jankovic");
        administratorCreateDTO.setCity("Trebinje");
        administratorCreateDTO.setStreet("Republike Srpske 17");
        administratorCreateDTO.setCountry("BiH");
        administratorCreateDTO.setPostal_code(81100);
        administratorCreateDTO.setEmail("ivanaj0610+v@gmail.com");
        administratorCreateDTO.setPhoneNumber("065");
        administratorCreateDTO.setIsPredefined(false);
        administratorCreateDTO.setType(UserType.ADMINISTRATOR);
        administratorCreateDTO.setPassword("pass");

        Administrator administrator = new Administrator();
        administrator.setName("ivana");
        administrator.setSurname("jankovic");
        administrator.setAddress(new Address("Republike Srpske 17","Trebinje",81100,"BiH"));
        administrator.setEmail("ivanaj0610+v@gmail.com");
        administrator.setPhoneNumber("065");
        administrator.setIsPredefined(false);
        administrator.setType(String.valueOf(UserType.ADMINISTRATOR));
        administrator.setPassword("pass");
        administrator.setPoints(0);

        when(mapper.map(administratorCreateDTO,Administrator.class)).thenReturn(administrator);
        when(administratorRepository.save(administrator)).thenReturn(administrator);
        administratorService.createAdmin(administratorCreateDTO);

        when(administratorRepository.findByEmail("ivanaj0610+v@gmail.com")).thenReturn(administrator);
        Administrator admin = administratorRepository.findByEmail("ivanaj0610+v@gmail.com");
        assertEquals(admin.getName(), "ivana");

        verify(administratorRepository, times(1)).findByEmail("ivanaj0610+v@gmail.com");
        verify(administratorRepository, times(1)).save(administrator);
        verifyNoMoreInteractions(administratorRepository);

        verify(mapper, times(1)).map(administratorCreateDTO,Administrator.class);
        verifyNoMoreInteractions(mapper);

    }


    @Test
    @Transactional
    public void testSave(){

        Administrator administrator = new Administrator();
        administrator.setName("Pera");
        administrator.setSurname("Peric");
        administrator.setAddress(new Address("Republike Srpske 17","Trebinje",81100,"BiH"));
        administrator.setEmail("admin@gmail.com");
        administrator.setPhoneNumber("065");
        administrator.setIsPredefined(false);
        administrator.setType(String.valueOf(UserType.ADMINISTRATOR));
        administrator.setPassword("pass");
        administrator.setPoints(0);


        when(administratorRepository.save(administrator)).thenReturn(administrator);
        administratorService.save(administrator);

        when(administratorRepository.findByEmail("admin@gmail.com")).thenReturn(administrator);
        Administrator admin = administratorRepository.findByEmail("admin@gmail.com");
        assertEquals(admin.getName(), "Pera");
        verify(administratorRepository, times(1)).findByEmail("admin@gmail.com");
        verify(administratorRepository, times(1)).save(administrator);
        verifyNoMoreInteractions(administratorRepository);

    }


    @Test
    @Transactional
    public void testFindById(){

        Administrator administrator = new Administrator();
        administrator.setName("Pera");
        administrator.setSurname("Peric");
        administrator.setAddress(new Address("Republike Srpske 17","Trebinje",81100,"BiH"));
        administrator.setEmail("admin@gmail.com");
        administrator.setPhoneNumber("065");
        administrator.setIsPredefined(false);
        administrator.setType(String.valueOf(UserType.ADMINISTRATOR));
        administrator.setPassword("pass");
        administrator.setPoints(0);

        when(administratorRepository.findById(6)).thenReturn(Optional.of(administrator));
        Administrator a = administratorService.findOne(6);

        assertEquals(administrator, a);
        verify(administratorRepository, times(1)).findById(6);
        verifyNoMoreInteractions(administratorRepository);

    }



}
