package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.user.Role;
import ftn.mrs.isa.rentalapp.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findById(Long id) {
        return this.roleRepository.getById(id);
    }

    public List<Role> findByName(String name) {
        return this.roleRepository.findByName(name);
    }
}
