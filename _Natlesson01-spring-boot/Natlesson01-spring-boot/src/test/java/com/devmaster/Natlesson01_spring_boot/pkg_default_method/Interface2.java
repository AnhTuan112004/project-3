package com.devmaster.Natlesson01_spring_boot.pkg_default_method;

public interface Interface2 {
    default void method2() {
        System.out.println("Interface2.method2() được gọi");
    }
}