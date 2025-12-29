package K23CNT1.natProject3.service;

import K23CNT1.natProject3.dto.CustomUserDetailsDOT;
import K23CNT1.natProject3.entity.User;
import K23CNT1.natProject3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Tìm user trong DB
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng: " + username));

        // 2. Lấy Role
        Set<GrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority(user.getRole().getName())
        );

        // 3. SỬA TẠI ĐÂY: Trả về CustomUserDetails thay vì User mặc định
        return new CustomUserDetailsDOT(user, authorities);
    }
}