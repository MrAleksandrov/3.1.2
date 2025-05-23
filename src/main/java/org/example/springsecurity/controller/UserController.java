package org.example.springsecurity.controller;

import org.example.springsecurity.model.User;
import org.example.springsecurity.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public String showUsers(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }



}