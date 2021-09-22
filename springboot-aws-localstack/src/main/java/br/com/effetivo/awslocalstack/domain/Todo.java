package br.com.effetivo.awslocalstack.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Todo {
    private String title;
    private String description;
    private String imagePath;
    private String imageFileName;
}