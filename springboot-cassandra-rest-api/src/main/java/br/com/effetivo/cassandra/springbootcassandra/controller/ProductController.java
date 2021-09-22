package br.com.effetivo.cassandra.springbootcassandra.controller;

import br.com.effetivo.cassandra.springbootcassandra.ResourceNotFoundException;
import br.com.effetivo.cassandra.springbootcassandra.domain.Product;
import br.com.effetivo.cassandra.springbootcassandra.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductRepository productRepository;

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product){
        productRepository.save(product);
        return product;

    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") Long productId){
        Product product=productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product not found" + productId));
        return ResponseEntity.ok().body(product);
    }



    @GetMapping("/products")
    public List<Product> getProducts(){

        return productRepository.findAll();
    }

    @PutMapping("products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Long productId,
                                                 @RequestBody Product productDetails) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id:" + productId));
        product.setName(productDetails.getName());
        final Product updatedProduct = productRepository.save(product);
        return ResponseEntity.ok(updatedProduct);

    }

    @DeleteMapping("products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(value = "id") Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product not found: " + productId));
        productRepository.delete(product);
        return ResponseEntity.ok().build();
    }

}
