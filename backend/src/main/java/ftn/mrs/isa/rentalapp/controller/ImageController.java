package ftn.mrs.isa.rentalapp.controller;

import ftn.mrs.isa.rentalapp.dto.ImageDTO;
import ftn.mrs.isa.rentalapp.model.entity.EntityType;
import ftn.mrs.isa.rentalapp.model.entity.Image;
import ftn.mrs.isa.rentalapp.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    @Autowired
    private EntityService entityService;

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
        String picturePath = "src\\main\\resources\\static\\images\\"+imageName;
        try (OutputStream stream = new FileOutputStream(new File(picturePath).getCanonicalFile())) {
            stream.write(data);
        }

        Image image = new Image();
        image.setPath(imageName);
        image.setEntity(entity);
        image.setIsMainPhoto(false);
        entity.getImages().add(image);

        entityService.save(entity);
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

    @GetMapping(value = "/getImage/{name}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String name) {
        try {
            FileSystemResource imgFile = new FileSystemResource("src/main/resources/static/images/" + name);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(imgFile.getInputStream()));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/uploadImage")
    public ResponseEntity<String> savePhoto(@RequestParam("image") MultipartFile image) throws IOException{
        try {
            String name = "cottage";
            Files.copy(image.getInputStream(), Paths.get("src\\main\\resources\\static").resolve("images").resolve(name + ".jpg").toAbsolutePath());
            return new ResponseEntity<>(name, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
