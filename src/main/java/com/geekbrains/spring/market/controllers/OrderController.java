package com.geekbrains.spring.market.controllers;

import com.geekbrains.spring.market.beans.Cart;
import com.geekbrains.spring.market.entities.Order;
import com.geekbrains.spring.market.entities.User;
import com.geekbrains.spring.market.services.OrderService;
import com.geekbrains.spring.market.services.UserService;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {
    private Cart cart;
    private OrderService orderService;
    private UserService userService;


    @PostMapping("/confirm")
    @ResponseStatus(HttpStatus.OK)
    public void confirmOrder(Principal principal, @RequestParam String address){
        User user = userService.findUserByPhone(principal.getName()).get();
        Order order = new Order(user, cart, principal.getName(), address);
        orderService.saveOrder(order);
    }
}
