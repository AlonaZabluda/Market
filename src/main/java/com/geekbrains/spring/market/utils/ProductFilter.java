package com.geekbrains.spring.market.utils;

import com.geekbrains.spring.market.entities.Category;
import com.geekbrains.spring.market.entities.Product;
import com.geekbrains.spring.market.repositories.specifications.ProductSpecifications;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

@Getter
public class ProductFilter {
    private Specification<Product> spec;
    private StringBuilder filterDefinition;

    public ProductFilter(Map<String, String> map, List<Category> categories) {
        this.spec = Specification.where(null);
        this.filterDefinition = new StringBuilder();
        if(map.containsKey("min") && !map.get("min").isEmpty()){
            Integer minPrice = Integer.parseInt(map.get("min"));
            spec = spec.and(ProductSpecifications.priceGreaterOrEqualsThan(minPrice));
            filterDefinition.append("&min=").append(minPrice);
        }
        if (map.containsKey("max") && !map.get("max").isEmpty()){
            Integer maxPrice = Integer.parseInt(map.get("max"));
            spec = spec.and(ProductSpecifications.priceLesserOrEqualsThan(maxPrice));
            filterDefinition.append("&max=").append(maxPrice);
        }
        if (map.containsKey("title") && !map.get("title").isEmpty()){
            String title = map.get("title");
            spec = spec.and(ProductSpecifications.findByPartTitle(title));
            filterDefinition.append("&title=").append(title);
        }
        if (categories != null && !categories.isEmpty()){
            Specification categorySpec = null;
            for (Category c: categories) {
                if(categorySpec == null){
                    categorySpec = ProductSpecifications.findByCategory(c);
                }else{
                    categorySpec = categorySpec.or(ProductSpecifications.findByCategory(c));
                }
            }
            spec = spec.and(categorySpec);
        }
    }
}
