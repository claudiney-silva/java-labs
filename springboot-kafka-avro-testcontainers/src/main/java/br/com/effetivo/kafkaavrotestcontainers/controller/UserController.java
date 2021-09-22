package br.com.effetivo.kafkaavrotestcontainers.controller;

import br.com.effetivo.kafkaavrotestcontainers.avro.UserEvent;
import br.com.effetivo.kafkaavrotestcontainers.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public String kafkaMessage(@RequestBody UserEvent userEvent) {
        userService.send(userEvent);
        return "Success";
    }
}