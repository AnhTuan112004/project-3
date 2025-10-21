package com.nat.Natlesson02_spring_boot;


import com.nat.Natlesson02_spring_boot.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean("userdevmaster")
    public User initUser() {
        return new User(9999, "Anh Tuấn");
    }
}