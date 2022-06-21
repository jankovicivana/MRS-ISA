package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.dto.AdministratorCreateDTO;
import ftn.mrs.isa.rentalapp.dto.AdministratorDTO;
import ftn.mrs.isa.rentalapp.model.user.Administrator;
import ftn.mrs.isa.rentalapp.model.user.UserType;
import ftn.mrs.isa.rentalapp.repository.AdministratorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private ModelMapper mapper;

    public void save(Administrator admin) {
        administratorRepository.save(admin);
    }

    public Administrator findByEmail(String string) {return administratorRepository.findByEmail(string);}

    public Administrator findOne(Integer id) {
        return administratorRepository.findById(id).orElse(null);
    }

    public Administrator createAdmin(AdministratorCreateDTO administratorCreateDTO) {
        Administrator admin = mapper.map(administratorCreateDTO,Administrator.class);
        admin.setType(String.valueOf(UserType.ADMINISTRATOR));
        admin.setPasswordChanged(false);
        admin.setPoints(0);
        administratorRepository.save(admin);
        return admin;
    }

    public void updateAdmin(AdministratorDTO administratorDTO) {
        Administrator oldAdmin = findOne(administratorDTO.getId());
        Administrator a = mapper.map(administratorDTO,Administrator.class);
        a.setRoles(oldAdmin.getRoles());
        a.setPoints(0);
        administratorRepository.save(a);
    }
}
