package com.geekbrains.spring.market;


import com.geekbrains.spring.market.entities.User;
import com.geekbrains.spring.market.repositories.UserRepository;
import com.geekbrains.spring.market.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void findOneUserTest() {
        User userFromDB = new User();
        userFromDB.setFirstName("22222222");
        userFromDB.setEmail("john@mail.ru");

        Mockito.doReturn(Optional.of(userFromDB))
                .when(userRepository)
                .findUserByPhone("22222222");

        User userJohn = userService.findUserByPhone("22222222").get();
        Assertions.assertNotNull(userJohn);
        Mockito.verify(userRepository, Mockito.times(1)).findUserByPhone(ArgumentMatchers.eq("22222222"));
//        Mockito.verify(userRepository, Mockito.times(1)).findOneByPhone(ArgumentMatchers.any(String.class));
    }
}
