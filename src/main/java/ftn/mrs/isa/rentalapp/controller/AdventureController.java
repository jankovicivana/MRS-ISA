package ftn.mrs.isa.rentalapp.controller;


import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import ftn.mrs.isa.rentalapp.dto.AdventureDTO;
import ftn.mrs.isa.rentalapp.model.entity.Adventure;
import ftn.mrs.isa.rentalapp.service.AdventureService;
import lombok.RequiredArgsConstructor;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import sun.nio.cs.UTF_32;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class AdventureController {

    @Autowired
    private  AdventureService adventureService;
    private final Gson g = new Gson();


    @RequestMapping(
            value = "/addAdventure",
            method = RequestMethod.POST,
            produces = "text/plain;charset=UTF-8")
    public void addAdventure(@RequestBody String payload) throws Exception {
        payload = encode(payload);
        System.out.print(payload);
        AdventureDTO info = g.fromJson(payload, AdventureDTO.class);
        adventureService.addAdventure(info);

    }












    public String encode (String payload){
        payload = payload.replace("%7B","{");
        payload = payload.replace("%22","\"");
        payload = payload.replace("%3A",":");
        payload = payload.replace("%7D=","}");
        payload = payload.replace("%2C",",");
        return payload;
    }
}
