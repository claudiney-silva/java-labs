package effetivo.oauth2.springbootkeycloak.controller;

import effetivo.oauth2.springbootkeycloak.domain.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ProductController {

    private static List<Product> products;
    
    static {
        products = new ArrayList<>(List.of(
            Product.builder()
                .id(1L)
                .title("Prod 1")
                .price(9.99)
                .build(),
            Product.builder()
                .id(2L)
                .title("Prod 2")
                .price(3.47)
                .build()                
        ));
    }
    
    @GetMapping("/products")
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(products);
    }    

    @GetMapping("/products/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Teste funcionou");
    }     
    
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> findById(@PathVariable long id) {
        return ResponseEntity.ok(products.stream().filter(product -> product.getId().equals(id)).findFirst()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found")));        
    }

    @PostMapping("/products")
    public ResponseEntity<Product> save(@RequestBody Product product) {
        product.setId(ThreadLocalRandom.current().nextLong(3, 1000000));
        products.add(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }    
}