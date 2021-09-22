package br.com.effetivo.orders.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @GetMapping
    public ResponseEntity<Void> root(){
        return ResponseEntity.ok().build();
    }     

    @GetMapping("/api")
    public ResponseEntity<Void> api(){
        return ResponseEntity.ok().build();
    }    
}
