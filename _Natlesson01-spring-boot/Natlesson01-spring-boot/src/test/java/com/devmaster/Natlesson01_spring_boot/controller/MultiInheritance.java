package com.devmaster.Natlesson01_spring_boot.controller;

import com.devmaster.Natlesson01_spring_boot.MultiInheritance;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Lesson01SpingbootApplicationTests {

    // KHÔNG cần @Autowired ở đây nữa.

    @Test
    void contextLoads() {
        // Test này để đảm bảo ứng dụng có thể khởi tạo context thành công
    }

    @Test
    void testMultiInheritance() {
        System.out.println("\n--- Bắt đầu kiểm tra lớp MultiInheritance ---");

        // Tạo đối tượng trực tiếp tại đây, không cần @Autowired
        MultiInheritance mi = new MultiInheritance();

        mi.method1(); // Sẽ gọi method1() của lớp MultiInheritance
        mi.method2(); // Sẽ gọi method2() đã được override của lớp MultiInheritance

        System.out.println("--- Kết thúc kiểm tra ---\n");
    }
}