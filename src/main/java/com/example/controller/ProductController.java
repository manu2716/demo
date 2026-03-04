package com.example.controller;

import com.example.model.Product;
import com.example.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    //TODO: Replace return type with ResponseEntity
    @GetMapping
    public List<Product> getAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id){
        return service.findById(id);
    }

    @PostMapping
    public Product create(@RequestBody Product product){
        return service.save(product);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product){
        return service.update(id, product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
}

