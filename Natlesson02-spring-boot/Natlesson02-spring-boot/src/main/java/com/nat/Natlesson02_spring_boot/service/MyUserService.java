package com.nat.Natlesson02_spring_boot.service;


import com.nat.Natlesson02_spring_boot.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyUserService {
    private final List<User> users = new ArrayList<>();

    public MyUserService() {
        users.add(new User(2209, "Anh Tuấn"));
        users.add(new User(1001, "Văn An"));
        users.add(new User(1002, "Thị Bình"));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public User getUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<User> searchUserByName(String name) {
        return users.stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }
}