package br.com.effetivo.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller
public class HelloController {

    @Get("/hello/{name}")
    public String hello(String name) {
        return "sucesso " + name;
    }
}
