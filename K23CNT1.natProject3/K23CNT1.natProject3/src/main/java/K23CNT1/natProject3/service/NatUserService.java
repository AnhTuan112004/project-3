package K23CNT1.natProject3.service;


import K23CNT1.natProject3.entity.NatUser;
import K23CNT1.natProject3.repository.NatUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Cần Bean PasswordEncoder
import org.springframework.stereotype.Service;

@Service
public class NatUserService {

    @Autowired private NatUserRepository userRepo;
    @Autowired private PasswordEncoder passwordEncoder; // Inject từ SecurityConfig

    // Đăng ký tài khoản mới
    public void registerUser(NatUser user) {
        // 1. Kiểm tra trùng tên đăng nhập
        if (userRepo.existsByNatusername(user.getNatusername())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }

        // 2. Mã hóa mật khẩu trước khi lưu
        user.setNatpassword(passwordEncoder.encode(user.getNatpassword()));

        // 3. Set quyền mặc định là USER
        user.setNatrole("ROLE_USER");

        userRepo.save(user);
    }

    public NatUser findByUsername(String username) {
        return userRepo.findByNatusername(username).orElse(null);
    }
}