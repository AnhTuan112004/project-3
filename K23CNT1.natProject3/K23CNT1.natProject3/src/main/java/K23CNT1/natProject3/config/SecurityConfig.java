package K23CNT1.natProject3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Tắt CSRF (Giúp API và Upload file hoạt động trơn tru trong môi trường Dev)
                .csrf(csrf -> csrf.disable())

                // 2. CẤU HÌNH QUYỀN TRUY CẬP
                .authorizeHttpRequests(auth -> auth
                        // A. TÀI NGUYÊN TĨNH & UPLOADS (Quan trọng: Cho phép khách xem ảnh/nghe nhạc)
                        .requestMatchers(
                                "/css/**", "/js/**", "/images/**",
                                "/uploads/**",             // <--- Dòng này giúp khách nghe được nhạc
                                "/assets/**", "/vendor/**"
                        ).permitAll()

                        // B. CÁC TRANG CÔNG KHAI
                        .requestMatchers(
                                "/", "/home",              // Trang chủ
                                "/login", "/register",     // Đăng nhập/Đăng ký
                                "/studios", "/studios/**", "/studio/**", // Xem chi tiết phòng
                                "/posts", "/posts/**", "/post/**",       // Xem chi tiết bài viết
                                "/api/**",
                                "/error"// API (nếu có)
                        ).permitAll()

                        // C. TRANG QUẢN TRỊ (ADMIN)
                        // Chấp nhận cả role "ADMIN" hoặc "ROLE_ADMIN" để tránh lỗi lệch tên trong DB
                        .requestMatchers("/admin/**").hasAnyAuthority("ADMIN", "ROLE_ADMIN")

                        // D. CÁC TRANG CÒN LẠI (Đặt lịch, User Profile...) -> BẮT BUỘC LOGIN
                        .anyRequest().authenticated()
                )

                // 3. CẤU HÌNH ĐĂNG NHẬP
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/perform_login")
                        .defaultSuccessUrl("/", true) // Mặc định về trang chủ
                        .failureUrl("/login?error=true")
                        .permitAll()
                )

                // 4. CẤU HÌNH ĐĂNG XUẤT
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout=true")
                        .deleteCookies("JSESSIONID") // Xóa cookie phiên làm việc
                        .permitAll()
                )

                // 5. XỬ LÝ LỖI QUYỀN HẠN (403 Forbidden)
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/403") // Bạn cần tạo file templates/403.html
                );

        return http.build();
    }
}