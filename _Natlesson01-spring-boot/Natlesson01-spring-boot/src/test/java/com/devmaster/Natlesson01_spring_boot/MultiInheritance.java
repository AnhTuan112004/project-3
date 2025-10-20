package com.devmaster.Natlesson01_spring_boot;


import com.devmaster.Natlesson01_spring_boot.pkg_default_method.Interface1;
import com.devmaster.Natlesson01_spring_boot.pkg_default_method.Interface2;

public class MultiInheritance implements Interface1, Interface2 {

    @Override
    public void method1() {
        System.out.println("MultiInheritance.method1() được gọi");
    }

    /**
     * Phương thức này BẮT BUỘC phải được @Override
     * vì cả Interface1 và Interface2 đều có default method tên là method2().
     * Việc override này để giải quyết sự không rõ ràng (ambiguity).
     */
    @Override
    public void method2() {
        // Bạn có thể chọn gọi một implementation cụ thể từ interface cha nếu muốn
        // Interface1.super.method2();
        System.out.println("MultiInheritance.method2() được gọi để giải quyết xung đột");
    }
}