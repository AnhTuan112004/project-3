package K23CNT1.natProject3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync; // [QUAN TRỌNG] 1. Import thư viện Async

@SpringBootApplication
@EnableAsync // [QUAN TRỌNG] 2. Kích hoạt tính năng chạy bất đồng bộ (cho AI Service)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}