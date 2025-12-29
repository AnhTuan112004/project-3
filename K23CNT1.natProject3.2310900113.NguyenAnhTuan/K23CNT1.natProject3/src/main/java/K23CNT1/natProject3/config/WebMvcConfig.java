package K23CNT1.natProject3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 1. Tạo đường dẫn tới thư mục "uploads" nằm ở gốc dự án
        Path uploadDir = Paths.get("uploads");

        // 2. Chuyển đổi sang URI (Ví dụ: file:///C:/Users/.../uploads/)
        // Cách này giúp chạy đúng trên cả Windows, Linux và MacOS mà không lo lỗi dấu gạch chéo
        String uploadPath = uploadDir.toUri().toString();

        // 3. Đăng ký Resource Handler
        // Khi truy cập URL: http://localhost:8080/uploads/ten_file.mp3
        // Spring sẽ tìm file trong thư mục vật lý "uploads"
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath);
    }
}