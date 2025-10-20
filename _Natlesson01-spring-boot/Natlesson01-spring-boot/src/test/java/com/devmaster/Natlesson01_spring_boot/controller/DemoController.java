package com.devmaster.Natlesson01_spring_boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/")
    public String welcome() {
        return "Chào mừng đến với Spring Boot!";
    }
}