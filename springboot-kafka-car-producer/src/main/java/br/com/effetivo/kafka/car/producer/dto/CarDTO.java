package br.com.effetivo.kafka.car.producer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDTO {

    private String id;
    private String model;
    private String color;

}
