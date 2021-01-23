package com.geekbrains.spring.market.services;

import com.geekbrains.spring.market.entities.Product;
import com.geekbrains.spring.market.entities.dtos.ProductDto;
import com.geekbrains.spring.market.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductsService {
    private ProductsRepository productsRepository;

    @Autowired
    public void setProductRepository(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }


    public Product findByTitle(String title){
        return productsRepository.findByTitle(title);
    }

    public Optional<Product> findById(Long id){
        return productsRepository.findById(id);
    }

    public List<Product> findAll(){
        return productsRepository.findAll();
    }

    public Page<Product> findAll(Specification<Product> spec, Integer number){
        if(number < 1) number = 1;
        return productsRepository.findAll(spec, PageRequest.of(number - 1, 7));
    }

    public Product saveOrUpdate(Product product){
        return productsRepository.save(product);
    }

    public void deleteAll(){
        productsRepository.deleteAll();
    }

    public void deleteProductById(Long id){
        productsRepository.deleteById(id);
    }

    public boolean existsById(Long id){
        return productsRepository.existsById(id);
    }

    public List<ProductDto> getAllDtos(){
        return productsRepository.getAllBy();
    }
}
