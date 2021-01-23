package com.geekbrains.spring.market.services;

import com.geekbrains.spring.market.entities.Role;
import com.geekbrains.spring.market.entities.User;
import com.geekbrains.spring.market.entities.dtos.SystemUser;
import com.geekbrains.spring.market.exceptions.UserNotFoundException;
import com.geekbrains.spring.market.repositories.RoleRepository;
import com.geekbrains.spring.market.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findUserByPhone(String phone){
        return userRepository.findUserByPhone(phone);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User findUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("User with id - '%s' not found", id)));
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = findUserByPhone(phone).orElseThrow(() -> new UsernameNotFoundException(String.format("User with phone '%s' not found", phone)));
        return new org.springframework.security.core.userdetails.User(user.getPhone(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    @Transactional
    public User save(SystemUser systemUser) {
        User user = new User();
        findUserByPhone(systemUser.getPhone()).ifPresent((u) -> {
            throw new RuntimeException("User with phone " + systemUser.getPhone() + " is already exist");
        });
        user.setPhone(systemUser.getPhone());
        user.setPassword(passwordEncoder.encode(systemUser.getPassword()));
        user.setFirstName(systemUser.getFirstName());
        user.setLastName(systemUser.getLastName());
        user.setEmail(systemUser.getEmail());
        user.setRoles(Arrays.asList(roleService.findByName("ROLE_USER")));
        user.setEnabled(true);
        return userRepository.save(user);
    }

    public boolean isUserExists(String phone){
        return userRepository.existsUserByPhone(phone);
    }
}
