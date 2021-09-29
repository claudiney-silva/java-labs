package br.com.effetivo.aws.s3.service;

import br.com.effetivo.aws.s3.domain.ImageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements  ImageService {

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    private final FileStoreService fileStoreService;

    @Override
    public ImageDTO save(MultipartFile file) {

        // check if the file is empty
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }

        // check if the file is an image
        if (!Arrays.asList(
            IMAGE_PNG.getMimeType(),
            IMAGE_GIF.getMimeType(),
            IMAGE_JPEG.getMimeType()
        ).contains(file.getContentType())) {
            throw new IllegalStateException("File uploaded is not an valid image");
        }

        // Save image in S3
        String imagePath = UUID.randomUUID().toString();
        String imageFileName = file.getOriginalFilename();
        String imagePathRelative = String.format("%s/%s", imagePath, imageFileName);

        // get file metadata
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        ImageDTO imageDTO = ImageDTO
                .builder()
                .imagePath(imagePath)
                .imageFileName(imageFileName)
                .imagePathRelative(imagePathRelative)
                .build();

        try {
            fileStoreService.upload(
                    String.format("%s/%s", bucketName, imageDTO.getImagePath()),
                    imageDTO.getImageFileName(),
                    Optional.of(metadata),
                    file.getInputStream()
            );
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }

        return imageDTO;
    }

    @Override
    public byte[] download(String imagePath, String imageFileName) {
        return fileStoreService.download(imagePath, imageFileName);
    }

    @Override
    public List<ImageDTO> findAll() {
        List<ImageDTO> imageDTOS = new ArrayList<>();

        fileStoreService.findAllByBucketName(bucketName).forEach(key -> {
            String[] fileNames = (key.indexOf("/") > 0)
                    ? key.split("/")
                    : new String[]{"", "key"};

            imageDTOS.add(ImageDTO
                    .builder()
                    .imagePath(fileNames[0])
                    .imageFileName(fileNames[1])
                    .imagePathRelative(key)
                    .build()
            );

        });

        return imageDTOS;
    }

    @Override
    public void delete(String imagePath, String imageFileName) {
        fileStoreService.delete(bucketName, String.format("%s/%s", imagePath, imageFileName));
    }
}
