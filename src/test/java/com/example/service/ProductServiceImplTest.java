package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository repo;

    @InjectMocks
    private ProductServiceImpl service;

    @Test
    public void findAllTest(){

        Product p1 = new Product();
        p1.setName("Laptop");
        p1.setPrice(1000);

        Product p2 = new Product();
        p2.setName("TV");
        p2.setPrice(250);
        List<Product> mockProduct = List.of(p1,p2);
        //Expected
        when(repo.findAll()).thenReturn(mockProduct);

        //Actual
        List<Product> result = service.findAll();

        assertEquals(2, result.size());
        assertEquals("Laptop", result.get(0).getName());
    }

    @Test
    public void findAllReturnEmptyListTest(){

        //Arrangement
        when(repo.findAll()).thenReturn(List.of());

        //Actual
        List<Product> result = service.findAll();

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void findByIdReturnsProductTest(){

        Product p1 = new Product();
        p1.setName("Laptop");
        p1.setPrice(1000);

        when(repo.findById(1L)).thenReturn(Optional.of(p1));

        Product result = service.findById(1L);

        //assert
        assertNotNull(result);
        assertEquals("Laptop",result.getName());
    }

    @Test
    public void findByIdReturnsNullTest(){

        when(repo.findById(1L)).thenReturn(Optional.empty());

        Product result = service.findById(1L);

        //assert
        assertNull(result);

    }

    @Test
    public void saveProductTest(){

        Product product = new Product();
        product.setName("Laptop");
        product.setPrice(1000);

        when(repo.save(product)).thenReturn(product);

        Product result = service.save(product);

        //assert
        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        assertEquals(1000, result.getPrice());
    }
}
