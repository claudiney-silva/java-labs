package br.com.effetivo.grpccient.controller;

import br.com.effetivo.grpccient.service.GRPCClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingPongEndPoint {

    @Autowired
    private GRPCClientService grpcClientService;

    @GetMapping("/ping")
    public String ping() {
        return grpcClientService.ping();
    }
}
