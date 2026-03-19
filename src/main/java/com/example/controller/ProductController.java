package com.example.controller;

import com.example.model.Product;
import com.example.service.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// To implement logging using lombok
@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductServiceImpl service;

    //by default constructor inject applies as there is single constructor with single parameter
    public ProductController(ProductServiceImpl service) {
        this.service = service;
    }


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
        log.info("Fetching all products");
        List<Product> products = service.findAll();
        //check if the list is empty
        if(products.isEmpty()){
            log.warn("No products found");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        log.info("Products fetched: {}", products.size());
        return ResponseEntity.status(HttpStatus.OK)
                .body(products);
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
        log.info("Fetching product by Id");
        Product product = service.findById(id);
        if (product == null) {
            log.warn("No product found");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        log.info("Product Fetched");
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
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        //TODO: Added Validations but not working
        log.info("Adding Product to the Database");
        Product savedProduct = service.save(product);
        log.info("Product Added to DB");
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
        log.info("Updating the product details");
        Product updatedProduct = service.update(id, product);
        if (updatedProduct == null) {
            log.warn("No product found to be updated");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
        log.info("Product successfully updated");
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
        log.info("Deleting the product with id {}",id);
        boolean result = service.delete(id);
        //check if the product exists or not
        if(!result){
            log.warn("Product with id {} not found",id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("Product successfully deleted");
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}

