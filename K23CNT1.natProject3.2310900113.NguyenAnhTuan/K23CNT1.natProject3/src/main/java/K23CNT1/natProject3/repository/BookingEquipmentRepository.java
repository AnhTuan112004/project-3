package K23CNT1.natProject3.repository;

import K23CNT1.natProject3.entity.BookingEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingEquipmentRepository extends JpaRepository<BookingEquipment, Long> {
    // Tìm chi tiết thiết bị theo ID đơn đặt
    List<BookingEquipment> findByBookingId(Long bookingId);
}