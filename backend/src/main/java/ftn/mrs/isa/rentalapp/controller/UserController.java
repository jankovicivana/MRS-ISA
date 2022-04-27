package ftn.mrs.isa.rentalapp.controller;


import ftn.mrs.isa.rentalapp.dto.AdministratorCreateDTO;
import ftn.mrs.isa.rentalapp.dto.AdministratorDTO;
import ftn.mrs.isa.rentalapp.model.user.Administrator;
import ftn.mrs.isa.rentalapp.model.user.UserType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/isEmailAvailable")
    public ResponseEntity<Boolean> isEmailAvailable(@RequestBody String email) {
        System.out.print("dosol");
        return new ResponseEntity<>(false, HttpStatus.CREATED);

        //administratorService.save(admin);
        //return new ResponseEntity<>(mapper.map(admin, AdministratorDTO.class), HttpStatus.CREATED);

    }
}
