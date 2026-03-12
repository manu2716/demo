package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
/* TODO : Implement the class using interface
   SOLID Principle class should depend on abstraction and not concrete implementation 
*/
public class ProductService {

    private final ProductRepository repo;

    //spring boot automatically injects the dependency using CI from spring boot 3+
    //no need for @Autowired annotation
    public ProductService(ProductRepository repo){
        this.repo = repo;
    }

    public List<Product> findAll(){
        log.debug("Fetching all products from Repo");
        return repo.findAll();
    }

    public Product findById(Long id){
        return repo.findById(id).orElse(null);
    }

    public Product save(Product product){
        return repo.save(product);
    }

    public Product update(Long id, Product updatedProduct){
        return repo.findById(id)
                .map(p ->
                {
                    p.setName(updatedProduct.getName());
                    p.setPrice(updatedProduct.getPrice());
                    return repo.save(p);
                }).orElse(null);
    }

    public void delete(Long id){
        repo.deleteById(id);
    }

}
