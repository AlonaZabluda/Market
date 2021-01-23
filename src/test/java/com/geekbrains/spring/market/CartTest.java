package com.geekbrains.spring.market;

import com.geekbrains.spring.market.beans.Cart;
import com.geekbrains.spring.market.entities.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;


@SpringBootTest
public class CartTest {
    @Autowired
    private Cart cart;
    private Product product;
    private long pId;

    @BeforeEach
    public void createProductsAndAddToCart(){
        for (int i = 0; i < 10; i++) {
            product = new Product();
            pId = i / 2 + 1;
            product.setId(pId);
            product.setPrice(new BigDecimal(100 + pId * 10));
            product.setTitle("Product #" + pId);
            cart.add(product);
        }
    }

    @Test()
    public void cartFillingAndClearingTest() {
        Assertions.assertEquals(5, cart.getItems().size());
        cart.clear();
        Assertions.assertEquals(0, cart.getItems().size());
    }

    @Test
    public void removeProductFromCartTest(){
        cart.decrement(product);
        cart.decrement(product);
        Assertions.assertEquals(4, cart.getItems().size());
    }

    @Test
    public void removeProductLineByIdTest(){
        cart.removeProductLineById(pId);
        Assertions.assertEquals(4, cart.getItems().size());
    }

    @Test
    public void getTotalPriceTest(){
        Assertions.assertEquals(new BigDecimal("1300"), cart.getPrice());
    }

}