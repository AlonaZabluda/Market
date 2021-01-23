package com.geekbrains.spring.market.entities;

import com.geekbrains.spring.market.beans.Cart;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    public Order(User user, Cart cart, String phone, String address) {
        this.user = user;
        this.price = new BigDecimal(cart.getPrice().doubleValue());
        this.orderItems = new ArrayList<>();
        this.phone = phone;
        this.address = address;
        this.orderItems = new ArrayList<>();
        for(OrderItem o: cart.getItems()){
            o.setOrder(this);
            this.orderItems.add(o);
        }
        cart.clear();
    }
}
