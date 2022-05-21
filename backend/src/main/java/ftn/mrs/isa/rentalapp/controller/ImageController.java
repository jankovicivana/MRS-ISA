package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.ImageDTO;
import ftn.mrs.isa.rentalapp.dto.RuleDTO;
import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import ftn.mrs.isa.rentalapp.model.entity.Image;
import ftn.mrs.isa.rentalapp.model.entity.Rule;
import ftn.mrs.isa.rentalapp.service.AdventureService;
import ftn.mrs.isa.rentalapp.service.BoatService;
import ftn.mrs.isa.rentalapp.service.CottageService;
import ftn.mrs.isa.rentalapp.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/images")
public class ImageController {

    @Autowired
    private CottageService cottageService;

    @Autowired
    private BoatService boatService;

    @Autowired
    private AdventureService adventureService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/addImage")
    @PreAuthorize("hasAnyRole('fishingInstructor','cottageOwner','boatOwner')")
    public ResponseEntity<ImageDTO> addImage(@RequestBody ImageDTO imageDTO, Principal principal) throws IOException {
        if(imageDTO.getEntityId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        EntityType entity = cottageService.findOne(imageDTO.getEntityId());
        if(entity == null){
            entity = adventureService.findOne(imageDTO.getEntityId());
        }
        if(entity == null){
            entity = boatService.findOne(imageDTO.getEntityId());
        }
        if(entity == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        byte[] data;
        try {
            data = Base64.getDecoder().decode(imageDTO.getData().split(",")[1]);
        } catch(Exception e) {
            return null;
        }
        String imageName = imageDTO.getPath();
        String picturePath = "..\\frontend\\src\\assets\\images\\"+imageName;
        try (OutputStream stream = new FileOutputStream(new File(picturePath).getCanonicalFile())) {
            stream.write(data);
        }

        Image image = new Image();
        image.setPath(imageDTO.getPath());
        image.setEntity(entity);
        image.setIsMainPhoto(false);
        entity.getImages().add(image);

        imageService.save(image);
        return new ResponseEntity<>(mapper.map(image, ImageDTO.class),HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/deleteImage/{id}")
    @PreAuthorize("hasAnyRole('fishingInstructor','cottageOwner','boatOwner')")
    public  ResponseEntity<Void> deleteImage(@PathVariable Integer id,Principal principal){
        Image image = imageService.findOne(id);

        if(image != null){
            imageService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
