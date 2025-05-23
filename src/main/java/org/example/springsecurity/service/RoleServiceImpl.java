package org.example.springsecurity.service;

import org.example.springsecurity.model.Role;
import org.example.springsecurity.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional
    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public void addRole(Role role) {
        roleRepository.save(role);
    }
}
