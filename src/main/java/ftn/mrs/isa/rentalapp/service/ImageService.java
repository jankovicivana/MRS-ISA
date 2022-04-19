package ftn.mrs.isa.rentalapp.service;

import ftn.mrs.isa.rentalapp.dto.ImageDTO;
import ftn.mrs.isa.rentalapp.model.entity.Cottage;
import ftn.mrs.isa.rentalapp.model.entity.Image;
import ftn.mrs.isa.rentalapp.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public void save(Image image){imageRepository.save(image);}

    public Image findOne(Integer id){return imageRepository.findById(id).orElse(null);}

    public void remove(Integer id){ imageRepository.deleteById(id);}

    public Set<Image> createImageFromString(Set<String> imagesDTO, Cottage cottage) throws IOException {
        if (imagesDTO == null) {
            return null;
        }
        Set<Image> images = new HashSet<>();
        int count=1;
        for (String imageDTO: imagesDTO) {

            byte[] data;
            try {
                data = Base64.getDecoder().decode(imageDTO.split(",")[1]);
            } catch(Exception e) {
                return null;
            }
            String imageName = "cottage-"+cottage.getName()+"."+count+".jpg";
            String picturePath = "src\\main\\resources\\static\\images\\"+imageName;
            try (OutputStream stream = new FileOutputStream(new File(picturePath).getCanonicalFile())) {
                stream.write(data);
            }

            Image im = new Image();
            im.setPath(imageName);
            im.setEntity(cottage);
            images.add(im);
            count++;
        }
        return images;
    }

}
