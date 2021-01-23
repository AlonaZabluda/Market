package com.geekbrains.spring.market.beans;

import com.geekbrains.spring.market.entities.OrderItem;
import com.geekbrains.spring.market.entities.Product;
import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class Cart {
    private List<OrderItem> items;
    private BigDecimal price;

    @PostConstruct
    public void init() {
        items = new ArrayList<>();
    }

    public void clear(){
        items.clear();
        getTotalPrice();
    }

    public void add(Product product){
        for(OrderItem o: items){
            if(o.getProduct().getId().equals(product.getId())){
                o.increment();
                getTotalPrice();
                return;
            }
        }
        items.add(new OrderItem(product));
        getTotalPrice();
    }

    public void decrement(Product product){
        for(OrderItem o: items){
            if(o.getProduct().getId().equals(product.getId())){
                o.decrement();
                if(o.isEmpty()){
                    items.remove(o);
                }
                getTotalPrice();
                return;
            }
        }
    }
    

    public void removeProductLineById(Long productId){
        for (OrderItem o: items) {
            if(o.getProduct().getId().equals(productId)){
                items.remove(o);
                getTotalPrice();
                return;
            }
        }
    }

    public void getTotalPrice(){
        price = new BigDecimal(0.0);
        for (OrderItem o: items) {
            price = price.add(o.getPrice());
        }
    }


}
