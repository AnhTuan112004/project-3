package com.devmaster.Natlesson01_spring_boot.pkg_default_method;

public interface Shape {
    void draw(); // Phương thức trừu tượng

    default void setColor(String color) {
        System.out.println("Vẽ hình với màu: " + color);
    }
}