package com.geekbrains.spring.market.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
@Data
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private int quantity;

    public OrderItem(Product product) {
        this.product = product;
        this.quantity = 1;
        this.price = new BigDecimal(0).add(product.getPrice());
    }

    public void increment(){
        this.quantity++;
        this.price = new BigDecimal(quantity * product.getPrice().doubleValue());
    }

    public void decrement(){
        this.quantity--;
        this.price = new BigDecimal(quantity * product.getPrice().doubleValue());
    }

    public boolean isEmpty(){
        return quantity == 0;
    }
}
