package com.geekbrains.spring.market.repositories;

import com.geekbrains.spring.market.entities.Product;
import com.geekbrains.spring.market.entities.dtos.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<ProductDto> getAllBy();
    Product findByTitle(String title);
    Product findById(String id);

}
