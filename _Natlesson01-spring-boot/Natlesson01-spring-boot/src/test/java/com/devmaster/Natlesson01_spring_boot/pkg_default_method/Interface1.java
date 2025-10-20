package com.devmaster.Natlesson01_spring_boot.pkg_default_method;

public interface Interface1 {
    void method1();

    default void method2() {
        System.out.println("Interface1.method2() được gọi");
    }
}