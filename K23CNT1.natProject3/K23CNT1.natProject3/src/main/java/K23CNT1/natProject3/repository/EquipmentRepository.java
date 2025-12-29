package K23CNT1.natProject3.repository;

import K23CNT1.natProject3.entity.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    // Gợi ý thiết bị theo tag (nat_category_tag)
    List<Equipment> findByCategoryTag(String categoryTag);

    // Kiểm tra tồn kho (nat_quantity)
    List<Equipment> findByQuantityGreaterThan(int quantity);
    // Tìm kiếm theo tên (Không phân trang)
    List<Equipment> findByNameContainingIgnoreCase(String name);

    // Tìm kiếm theo tên (Có phân trang)
    Page<Equipment> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
