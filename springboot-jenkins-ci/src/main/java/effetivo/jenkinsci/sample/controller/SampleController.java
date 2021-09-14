package effetivo.jenkinsci.sample.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import effetivo.jenkinsci.sample.domain.Auth;

@RestController
public class SampleController {

    @GetMapping
    public ResponseEntity<List<Auth>> findAll() {
        return ResponseEntity.ok(List.of(new Auth(1L, "John", "Doe"), new Auth(2L, "David", "Norton")));
    }    
    
}