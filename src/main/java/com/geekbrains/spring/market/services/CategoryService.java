package com.geekbrains.spring.market.services;

import com.geekbrains.spring.market.entities.Category;
import com.geekbrains.spring.market.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public List<Category> findById(List<Long> id){
        return categoryRepository.findAllById(id);
    }
}
