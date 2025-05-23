package org.example.springsecurity.service;


import org.example.springsecurity.model.User;
import java.util.List;

public interface UserService {
    List<User> findAll();

    void save(User user);

    void update(User user);

    void delete(Long id);

    User findById(Long id);

    User getUserByUsername(String userName);
}
