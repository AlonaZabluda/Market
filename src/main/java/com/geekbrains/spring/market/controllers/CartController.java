package com.geekbrains.spring.market.controllers;

import com.geekbrains.spring.market.beans.Cart;
import com.geekbrains.spring.market.entities.Product;
import com.geekbrains.spring.market.entities.dtos.OrderItemDto;
import com.geekbrains.spring.market.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.market.services.OrderItemService;
import com.geekbrains.spring.market.services.ProductsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
public class CartController {
    private OrderItemService orderItemService;
    private Cart cart;
    private ProductsService productsService;

    @GetMapping("/add/{product_id}")
    public void addProductToCart(@PathVariable(value = "product_id") Long id) {
        Product product = productsService.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Unable to add product (id = \" + productId + \" ) to cart. Product not found."));
        cart.add(product);
    }

    @GetMapping
    public List<OrderItemDto> getCartContent() {
        return orderItemService.mapEntityListToDtoList(cart.getItems());
    }

}

