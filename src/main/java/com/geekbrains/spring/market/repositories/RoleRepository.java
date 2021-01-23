package com.geekbrains.spring.market.repositories;

import com.geekbrains.spring.market.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
