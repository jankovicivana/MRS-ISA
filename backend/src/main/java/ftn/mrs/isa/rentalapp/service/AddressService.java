package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.model.user.Address;
import ftn.mrs.isa.rentalapp.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Address> findAll() {
        return addressRepository.findAll();
    }
}
