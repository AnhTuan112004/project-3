package com.devmaster.Natlesson01_spring_boot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Natlesson01SpringBootApplicationTests {

    @Test
    void contextLoads() {
        // Test này giữ nguyên để đảm bảo ứng dụng có thể khởi động.
    }

    /**
     * Thêm phương thức test này vào để kiểm tra lớp MultiInheritance.
     */
    @Test
    void testMultiInheritanceLogic() {
        System.out.println("\n--- Bắt đầu kiểm tra lớp MultiInheritance ---");

        // 1. Tạo một đối tượng từ lớp MultiInheritance
        MultiInheritance mi = new MultiInheritance();

        // 2. Gọi các phương thức của nó để xem kết quả
        System.out.print("Gọi mi.method1(): ");
        mi.method1();

        System.out.print("Gọi mi.method2(): ");
        mi.method2();

        System.out.println("--- Kết thúc kiểm tra ---\n");
    }
}