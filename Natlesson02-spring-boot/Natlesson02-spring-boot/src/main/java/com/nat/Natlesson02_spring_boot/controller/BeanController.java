package com.nat.Natlesson02_spring_boot.controller;


import com.nat.Natlesson02_spring_boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeanController {

    @Autowired
    @Qualifier("userdevmaster")
    private User myUser;

    @GetMapping("/bean")
    public String getBeanInfo() {
        return "Bean User: " + myUser.getName() + " - ID: " + myUser.getId();
    }
}