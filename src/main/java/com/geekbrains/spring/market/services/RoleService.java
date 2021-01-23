package com.geekbrains.spring.market.services;

import com.geekbrains.spring.market.entities.Role;
import com.geekbrains.spring.market.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String role){
        return roleRepository.findByName(role);
    }
}
