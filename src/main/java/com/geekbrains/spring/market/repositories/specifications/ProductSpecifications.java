package com.geekbrains.spring.market.repositories.specifications;

import com.geekbrains.spring.market.entities.Category;
import com.geekbrains.spring.market.entities.Product;
import org.springframework.data.jpa.domain.Specification;


public class ProductSpecifications {
    public static Specification<Product> priceGreaterOrEqualsThan(int price){
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> priceLesserOrEqualsThan(int price){
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> findByPartTitle(String title){
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), String.format("%%%s%%", title).toLowerCase());
    }

    public static Specification<Product> findByCategory(Category categ){
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isMember(categ, root.get("categories"));
    }

}
