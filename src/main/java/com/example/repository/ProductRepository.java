package com.example.repository;

import com.example.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Implementing JpaRepository as it contains both CrudRepository and PagingAndSortingRepository
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    //TODO : Add custom methods such as find product by name, price etc.
}
