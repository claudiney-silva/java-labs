package br.com.effetivo.aws.s3.controller;

import br.com.effetivo.aws.s3.domain.ImageDTO;
import br.com.effetivo.aws.s3.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("${app.path}")
@CrossOrigin
@RequiredArgsConstructor
public class ImageController {

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Value("${app.path}")
    private String path;

    private final ImageService imageService;

    @GetMapping
    public ResponseEntity<List<ImageDTO>> findAll() {
        List<ImageDTO> imageDTOS = imageService.findAll();
        imageDTOS.forEach(imageDTO -> {
            imageDTO.setImagePathRelative(String.format("%s/%s", path, imageDTO.getImagePathRelative()));
        });
        return new ResponseEntity<>(imageDTOS, HttpStatus.OK);
    }

    @PostMapping(
            path = "",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ImageDTO> save(@RequestParam("file") MultipartFile file) {
        ImageDTO imageDTO = imageService.save(file);
        imageDTO.setImagePathRelative(
                String.format("%s/%s", path, imageDTO.getImagePathRelative())
        );
        return new ResponseEntity<>(imageDTO, HttpStatus.OK);
    }

    @GetMapping(
            value = "/{imagePath}/{imageFileName}",
            produces = {
                    MediaType.IMAGE_PNG_VALUE,
                    MediaType.IMAGE_GIF_VALUE,
                    MediaType.IMAGE_JPEG_VALUE
            }
    )
    public byte[] download(@PathVariable("imagePath") String imagePath,
                           @PathVariable("imageFileName") String imageFileName) {
        return imageService.download(String.format("%s/%s", bucketName, imagePath), imageFileName);
    }

    @DeleteMapping(
            value = "/{imagePath}/{imageFileName}",
            produces = {
                    MediaType.IMAGE_PNG_VALUE,
                    MediaType.IMAGE_GIF_VALUE,
                    MediaType.IMAGE_JPEG_VALUE
            }
    )
    public ResponseEntity<Void> delete(@PathVariable("imagePath") String imagePath,
                           @PathVariable("imageFileName") String imageFileName) {
        imageService.delete(imagePath, imageFileName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
