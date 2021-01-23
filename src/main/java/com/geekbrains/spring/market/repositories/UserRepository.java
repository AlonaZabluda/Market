package com.geekbrains.spring.market.repositories;

import com.geekbrains.spring.market.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByPhone(String phone);
    boolean existsUserByPhone(String phone);
}
