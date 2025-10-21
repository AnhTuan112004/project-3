package com.nat.Natlesson02_spring_boot.service;

import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl implements GreetingService {
    @Override
    public String greet(String name) {
        return "<h1>Xin chào, " + name + "</h1>"
                + "<h2 style='color:red; text-align:center'>Chúc mừng bạn đã hoàn thành bài Lab!</h2>";
    }
}