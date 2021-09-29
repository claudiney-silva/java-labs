package br.com.effetivo.aws.s3.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDTO {
    private String imagePath;
    private String imageFileName;
    private String imagePathRelative;
}
