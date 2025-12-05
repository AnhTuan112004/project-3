package K23CNT1.natProject3.repository;


import K23CNT1.natProject3.entity.NatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NatUserRepository extends JpaRepository<NatUser, Long> {

    // Tìm user theo username (Dùng cho Spring Security loadUserByUsername)
    Optional<NatUser> findByNatusername(String natusername);

    // Kiểm tra user đã tồn tại chưa (Dùng khi Đăng ký)
    boolean existsByNatusername(String natusername);

    // Tìm danh sách nhân viên (Dùng cho Admin phân công việc)
    // List<NatUser> findByNatrole(String role);
}