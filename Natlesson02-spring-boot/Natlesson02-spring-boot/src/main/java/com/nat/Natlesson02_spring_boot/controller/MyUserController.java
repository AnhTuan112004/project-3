package com.nat.Natlesson02_spring_boot.controller;


import com.nat.Natlesson02_spring_boot.model.User;
import com.nat.Natlesson02_spring_boot.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyUserController {

    @Autowired
    private MyUserService myUserService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return myUserService.getAllUsers();
    }

    @GetMapping("/users/search")
    public List<User> getUserByName(@RequestParam(value = "name", required = false) String name) {
        if (name == null || name.isEmpty()) {
            return myUserService.getAllUsers();
        }
        return myUserService.searchUserByName(name);
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") int userId) {
        return myUserService.getUserById(userId);
    }
}