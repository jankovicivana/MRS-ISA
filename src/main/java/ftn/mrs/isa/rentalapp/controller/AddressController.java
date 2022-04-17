package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;
}
