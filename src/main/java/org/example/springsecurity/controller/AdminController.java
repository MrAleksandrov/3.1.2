package org.example.springsecurity.controller;


import org.example.springsecurity.model.Role;
import org.example.springsecurity.model.User;
import org.example.springsecurity.service.RoleService;
import org.example.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
    @RequestMapping("/admin")
    public class AdminController {

        private final UserService userService;
        private final RoleService roleService;
        @Autowired
        private PasswordEncoder passwordEncoder;


        @Autowired
        public AdminController(UserService userService, RoleService roleService) {
            this.userService = userService;
            this.roleService = roleService;
        }

        @GetMapping
        public String showUsers(Model model, Principal principal) {
            if (principal == null) {
                return "redirect:/login";
            }
            User user = userService.getUserByUsername(principal.getName());
            model.addAttribute("users", userService.findAll());
            return "admin";
        }


    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getRoles());
        return "addUser";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "roles", required = false) List<Long> roleIds) {
        Set<Role> roles = new HashSet<>();
        if (roleIds != null) {
            for (Long roleId : roleIds) {
                Role role = roleService.findById(roleId);
                if (role != null) {
                    roles.add(role);
                }
            }
        }
        user.setRoles(roles);
        String newPassword = user.getPassword();
        if (newPassword == null || newPassword.isEmpty()) {
            newPassword = user.getUsername();
        }
        user.setPassword(passwordEncoder.encode(newPassword));

        userService.save(user);
        return "redirect:/admin";
    }


        @GetMapping("/edit")
        public String editUserForId(@RequestParam(value = "userId") Long id, Model model) {
            User user = userService.findById(id);
            List<Role> allRoles = roleService.getRoles();
            model.addAttribute("user", user);
            model.addAttribute("allRoles", allRoles);
            return "edit";
        }


    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") User user,
                           @RequestParam("userId") Long id,
                           @RequestParam(value = "roles", required = false) List<Long> roleIds) {
        user.setId(id);

        Set<Role> roles = new HashSet<>();
        if (roleIds != null) {
            for (Long roleId : roleIds) {
                Role role = roleService.findById(roleId);
                if (role != null) {
                    roles.add(role);
                }
            }
        }
        user.setRoles(roles);

        User oldUser = userService.findById(id);

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(oldUser.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userService.save(user);
        return "redirect:/admin";
    }

        @RequestMapping("/deleteUser")
        public String deleteUser(@RequestParam("userId") Long id) {
            userService.delete(id);
            return "redirect:/admin";
        }
    }