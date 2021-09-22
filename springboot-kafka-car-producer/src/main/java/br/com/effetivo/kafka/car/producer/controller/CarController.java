package br.com.effetivo.kafka.car.producer.controller;

import br.com.effetivo.kafka.car.producer.dto.CarDTO;
import br.com.effetivo.kafka.car.producer.service.CarProducer;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarProducer carProducer;

    @PostMapping
    public ResponseEntity<CarDTO> create(@RequestBody CarDTO carDTO){
        CarDTO car = CarDTO.builder().id(UUID.randomUUID().toString()).color(carDTO.getColor()).model(carDTO.getModel()).build();
        carProducer.send(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }

}