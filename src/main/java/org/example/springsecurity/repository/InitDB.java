package org.example.springsecurity.repository;


import org.example.springsecurity.model.Role;
import org.example.springsecurity.model.User;
import org.example.springsecurity.service.RoleService;
import org.example.springsecurity.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Collections;



@Component
public class InitDB {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public InitDB(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    @Transactional
    public void init() {

        // Создание ролей
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleService.addRole(adminRole);

        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        roleService.addRole(userRole);

        // Добавляем 1 Admin
        User admin = new User();
        admin.setUsername("admin");
        admin.setSurName("Adminov");
        admin.setAge(30);
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles(Collections.singleton(adminRole));
        userService.save(admin);

        // Добавляем 4 User
        for (int i = 1; i <= 4; i++) {
            User user = new User();
            user.setUsername("user" + i);
            user.setSurName("Userov" + i);
            user.setAge(20 + i);
            user.setPassword(passwordEncoder.encode("user" + i));
            user.setRoles(Collections.singleton(userRole));
            userService.save(user);
        }

        System.out.println("Тестовые данные успешно добавлены в базу данных");
    }
}