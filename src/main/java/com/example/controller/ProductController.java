package com.example.controller;

import com.example.model.Product;
import com.example.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO: Implement Logging
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    //by default constructor inject applies as there is single constructor with single parameter
    public ProductController(ProductService service) {
        this.service = service;
    }

    //TODO: Refactor GET to check for null or empty list
    @Operation(
            summary = "Get All Product",
            description = "Fetch all the products"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of Products"),
            @ApiResponse(responseCode = "404", description = "No products found")
    })
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Product by ID",
            description = "Fetch a single product by unique id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Product> getById(@PathVariable Long id) {

        Product product = service.findById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(product);
    }

    @PostMapping
    @Operation(
            summary = "Add a Product",
            description = "Adds a new product to the database"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product added"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Product savedProduct = service.save(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedProduct);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a product",
            description = "Updates the details of the product"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated"),
            @ApiResponse(responseCode = "404", description = "Invalid Product")
    })
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = service.update(id, product);
        if (updatedProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a Product",
            description = "Deletes a product from the database by passing it's unique id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product Deleted"),
            @ApiResponse(responseCode = "404", description = "Product Not Found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}

