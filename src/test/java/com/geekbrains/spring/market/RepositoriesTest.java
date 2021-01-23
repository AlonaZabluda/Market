package com.geekbrains.spring.market;

import com.geekbrains.spring.market.entities.Product;
import com.geekbrains.spring.market.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.market.repositories.ProductsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class RepositoriesTest {
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void initDbTest() {
        List<Product> productsList = productsRepository.findAll();
        Assertions.assertEquals(3, productsList.size());
    }

    @Test
    public void addProductToRepositoryTest() {
        Product product = new Product("cherry", new BigDecimal("12.70"));
        Product out = entityManager.persist(product);
        entityManager.flush();
        List<Product> productsList = productsRepository.findAll();

        Assertions.assertEquals(4, productsList.size());
        Assertions.assertEquals("cherry", productsList.get(3).getTitle());
    }

    @Test
    public void findProductByIdTest(){
        Product product = productsRepository.findById(2L).get();
        Assertions.assertEquals( new BigDecimal("13.50"), product.getPrice());
    }

    @Test
    public void deleteProductByIdTest(){
        productsRepository.deleteById(1L);
        List<Product> productList = productsRepository.findAll();
        Assertions.assertEquals(2, productList.size());
    }


}
