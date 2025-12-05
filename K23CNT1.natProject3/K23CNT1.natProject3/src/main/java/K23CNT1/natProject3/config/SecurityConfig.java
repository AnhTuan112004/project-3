package K23CNT1.natProject3.config;


import K23CNT1.natProject3.repository.NatUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Mã hóa mật khẩu (BCrypt là chuẩn an toàn hiện nay)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Cấu hình luồng bảo mật (Filter Chain)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Tắt CSRF để test API dễ hơn (thực tế nên bật)
                .authorizeHttpRequests(auth -> auth
                        // -- KHU VỰC CÔNG KHAI (Ai cũng vào được) --
                        .requestMatchers("/", "/room/**", "/calendar", "/api/**").permitAll()
                        .requestMatchers("/login", "/register", "/css/**", "/js/**", "/images/**", "/uploads/**").permitAll()

                        // -- KHU VỰC ADMIN (Chỉ Role ADMIN) --
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // -- KHU VỰC ĐĂNG NHẬP (User & Admin) --
                        .requestMatchers("/booking/**", "/payment/**").authenticated()

                        // Các link khác bắt buộc đăng nhập
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")        // Đường dẫn trang login của mình
                        .defaultSuccessUrl("/")     // Login thành công về trang chủ
                        .failureUrl("/login?error") // Login sai
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    // 3. Cấu hình cách lấy User từ Database để kiểm tra đăng nhập
    @Bean
    public UserDetailsService userDetailsService(NatUserRepository userRepo) {
        return username -> userRepo.findByNatusername(username)
                .map(natUser -> new User(
                        natUser.getNatusername(),
                        natUser.getNatpassword(),
                        // Chuyển role từ String sang GrantedAuthority (Spring yêu cầu)
                        Collections.singletonList(new SimpleGrantedAuthority(natUser.getNatrole()))
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user: " + username));
    }
}