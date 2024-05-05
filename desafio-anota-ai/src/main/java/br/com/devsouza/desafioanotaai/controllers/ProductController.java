package br.com.devsouza.desafioanotaai.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.devsouza.desafioanotaai.domain.product.Product;
import br.com.devsouza.desafioanotaai.domain.product.ProductDTO;
import br.com.devsouza.desafioanotaai.services.ProductService;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Product> insert(@RequestBody ProductDTO data) {
        Product objectSaved = this.service.insert(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(objectSaved);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        List<Product> list = this.service.getAll();
        return ResponseEntity.ok().body(list);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable String id, @RequestBody ProductDTO data) {
        Product updatedObj = this.service.update(id, data);
        return ResponseEntity.ok().body(updatedObj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
