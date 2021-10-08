package br.com.effetivo.serviceone.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RefreshScope // refresh scope do spring config
@RestController
@RequestMapping("/secure")
public class SecureController {

    @Value("${hello.message}")
    private String helloMessage;

    @GetMapping
    public String secure(Principal principal) {
        return helloMessage;
    }
}