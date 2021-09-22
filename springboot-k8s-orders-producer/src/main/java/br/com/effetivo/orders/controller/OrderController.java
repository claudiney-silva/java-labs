package br.com.effetivo.orders.controller;

import java.util.UUID;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.effetivo.orders.domain.Order;
import br.com.effetivo.orders.service.OrderProducer;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderProducer orderProducer;

    @GetMapping
    public ResponseEntity<List<Order>> findAll(){
        Order order1 = Order.builder().id(UUID.randomUUID().toString()).comments("First order").build();
        return ResponseEntity.ok(List.of(order1));
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order order){
        order.setId(UUID.randomUUID().toString());
        orderProducer.send(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }    
}
