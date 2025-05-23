package org.example.springsecurity.service;

import org.example.springsecurity.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();

    Role findById(Long id);

    Role findByName(String name);

    void addRole(Role role);
}