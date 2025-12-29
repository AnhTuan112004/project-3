package K23CNT1.natProject3.repository;

import K23CNT1.natProject3.entity.Studio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Long> {

    // ================== PHƯƠNG THỨC CHO PHÂN TRANG (HOME PAGE) ==================

    // 1. Lấy tất cả phòng (Mặc định JpaRepository đã có findAll(Pageable),
    // nhưng nếu muốn lọc theo Status 'ACTIVE' + Phân trang thì dùng cái này:
    Page<Studio> findByStatus(String status, Pageable pageable);

    // 2. Tìm kiếm theo tên + Phân trang (Quan trọng cho thanh tìm kiếm)
    Page<Studio> findByNameContainingIgnoreCase(String name, Pageable pageable);


    // ================== PHƯƠNG THỨC CŨ (TRẢ VỀ LIST) ==================
    // (Giữ lại để dùng cho Admin hoặc các dropdown list không cần phân trang)

    List<Studio> findByStatus(String status);

    List<Studio> findByStudioTypeAndStatus(String studioType, String status);

    // Tìm kiếm thường (không phân trang)
    List<Studio> findByNameContainingIgnoreCase(String name);
}