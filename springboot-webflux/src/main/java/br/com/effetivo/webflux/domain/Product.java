package br.com.effetivo.webflux.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Table("app_user")
public class Product {
    @Id
    private Integer id;

    @NotNull
    @NotEmpty(message = "The name of this course cannot be empty")
    private String name;
}
