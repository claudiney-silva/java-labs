package br.com.effetivo.kafkaavrotestcontainers.controller;

import br.com.effetivo.kafkaavrotestcontainers.avro.UserEventCreate;
import br.com.effetivo.kafkaavrotestcontainers.avro.UserEventUpdate;
import br.com.effetivo.kafkaavrotestcontainers.service.KafkaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
@AllArgsConstructor
public class UserController {

    private final KafkaService kafkaService;

    @PostMapping
    public String kafkaMessage(@RequestBody UserEventCreate userEventCreate) {
        kafkaService.send(userEventCreate.getId(), userEventCreate);
        return "UserEventCreate";
    }

    @PutMapping
    public String kafkaMessage(@RequestBody UserEventUpdate userEventUpdate) {
        kafkaService.send(userEventUpdate.getId(), userEventUpdate);
        return "UserEventUpdate";
    }
}