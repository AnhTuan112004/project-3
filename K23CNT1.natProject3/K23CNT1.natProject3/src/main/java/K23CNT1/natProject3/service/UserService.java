package K23CNT1.natProject3.service;

import K23CNT1.natProject3.entity.Role;
import K23CNT1.natProject3.entity.User;
import K23CNT1.natProject3.repository.RoleRepository;
import K23CNT1.natProject3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Đăng ký thành viên mới vào bảng nat_users
     */
    @Transactional
    public void registerUser(User user) {
        // 1. Mã hóa mật khẩu bảo mật
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 2. Gán Role mặc định là ROLE_USER (nat_role_id)
        Role roleUser = roleRepo.findByName("ROLE_USER")
                .orElseGet(() -> {
                    // Nếu DB chưa có Role này thì có thể tạo mới hoặc báo lỗi
                    return null;
                });
        user.setRole(roleUser);

        // 3. Khởi tạo các giá trị nat_rank_level và nat_total_spending
        user.setRankLevel("BRONZE");
        user.setTotalSpending(BigDecimal.ZERO);

        userRepo.save(user);
    }

    /**
     * [Feature 4] Cập nhật chi tiêu và hạng thành viên
     * Tự động chạy khi Admin xác nhận đơn hàng hoàn thành (COMPLETED)
     */
    @Transactional
    public void updateUserRank(Long userId, BigDecimal amountToAdd) {
        if (amountToAdd == null) return;

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với ID: " + userId));

        // 1. Cộng dồn chi tiêu (Xử lý an toàn nếu totalSpending đang null)
        BigDecimal currentSpending = user.getTotalSpending() != null ? user.getTotalSpending() : BigDecimal.ZERO;
        BigDecimal newTotal = currentSpending.add(amountToAdd);
        user.setTotalSpending(newTotal);

        // 2. Tính toán hạng mới dựa trên nat_total_spending
        String newRank = calculateRank(newTotal, user.getRankLevel());
        user.setRankLevel(newRank);

        userRepo.save(user);
    }

    /**
     * Logic phân hạng:
     * - Trên 20.000.000đ: GOLD (Giảm 10% khi đặt phòng)
     * - Trên 5.000.000đ: SILVER (Giảm 5% khi đặt phòng)
     * - Dưới 5.000.000đ: BRONZE
     */
    private String calculateRank(BigDecimal total, String currentRank) {
        BigDecimal goldThreshold = new BigDecimal("20000000");
        BigDecimal silverThreshold = new BigDecimal("5000000");

        if (total.compareTo(goldThreshold) >= 0) {
            return "GOLD";
        }

        if (total.compareTo(silverThreshold) >= 0) {
            // Chỉ nâng từ BRONZE lên SILVER, không hạ từ GOLD xuống SILVER
            if ("GOLD".equals(currentRank)) {
                return "GOLD";
            }
            return "SILVER";
        }

        return "BRONZE";
    }

    /**
     * Tìm kiếm User theo Username (Dùng cho Security hoặc Profile)
     */
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}