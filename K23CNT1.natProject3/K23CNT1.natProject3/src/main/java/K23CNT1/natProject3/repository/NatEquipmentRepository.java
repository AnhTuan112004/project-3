package K23CNT1.natProject3.repository;


import K23CNT1.natProject3.entity.NatEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NatEquipmentRepository extends JpaRepository<NatEquipment, Long> {
    // Tìm thiết bị có giá thuê nhỏ hơn X (Dùng lọc nâng cao nếu cần)
    List<NatEquipment> findByNatpriceLessThan(java.math.BigDecimal price);
}