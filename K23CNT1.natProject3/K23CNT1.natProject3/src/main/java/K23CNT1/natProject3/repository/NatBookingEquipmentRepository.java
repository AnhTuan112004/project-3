package K23CNT1.natProject3.repository;


import K23CNT1.natProject3.entity.NatBookingEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NatBookingEquipmentRepository extends JpaRepository<NatBookingEquipment, Long> {
    // Tìm tất cả thiết bị thuê thêm của 1 đơn hàng cụ thể
    List<NatBookingEquipment> findByNatBooking_Natid(Long bookingId);
}