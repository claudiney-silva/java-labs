package br.com.effetivo.jaegeropentelemetry.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("sample")
public class SampleController {

    @GetMapping(path = "{name}")
    public String get(@PathVariable String name) {
        log.info("Log para "+name);
        return "Bem vindo "+name;
    }

}
