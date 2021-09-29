package br.com.effetivo.aws.s3.service;

import br.com.effetivo.aws.s3.domain.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    public ImageDTO save(MultipartFile file);

    public byte[] download(String imagePath, String imageFileName);

    public List<ImageDTO> findAll();

    public void delete(String imagePath, String imageFileName);

}
