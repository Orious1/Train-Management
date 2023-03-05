package com.manage.trainsys.controller;

import com.manage.trainsys.entity.User;
import com.manage.trainsys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public User login(String account, String password) {
        try {
            User user = userService.login(account, password);
            return user;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }
}
