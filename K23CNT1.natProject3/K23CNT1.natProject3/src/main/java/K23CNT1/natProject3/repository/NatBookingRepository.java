package K23CNT1.natProject3.repository;


import K23CNT1.natProject3.entity.NatBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface NatBookingRepository extends JpaRepository<NatBooking, Long> {

    // 1. Lấy tất cả đơn hàng, sắp xếp mới nhất lên đầu (Admin)
    Page<NatBooking> findAllByOrderByNatcreatedAtDesc(Pageable pageable);

    // 2. Lấy lịch sử đặt phòng của một User cụ thể (Trang 'Lịch sử của tôi')
    Page<NatBooking> findByNatUser_NatidOrderByNatcreatedAtDesc(Long userId, Pageable pageable);

    // 3. THỐNG KÊ DOANH THU THEO THÁNG (Cho Biểu đồ Cột)
    // Trả về: [Tháng, Tổng tiền] của các đơn đã COMPLETED trong năm nay
    @Query("SELECT MONTH(b.natstartTime), SUM(b.nattotalAmount) " +
            "FROM NatBooking b " +
            "WHERE YEAR(b.natstartTime) = YEAR(CURRENT_DATE) AND b.natstatus = 'COMPLETED' " +
            "GROUP BY MONTH(b.natstartTime)")
    List<Object[]> getMonthlyRevenue();

    // 4. THỐNG KÊ SỐ LƯỢNG ĐẶT THEO PHÒNG (Cho Biểu đồ Tròn)
    // Trả về: [Tên phòng, Số lần đặt]
    @Query("SELECT b.natRoom.natname, COUNT(b) " +
            "FROM NatBooking b " +
            "GROUP BY b.natRoom.natname")
    List<Object[]> getRoomBookingStats();

    // 5. Tính tổng doanh thu toàn bộ (Hiển thị số to đùng ở Dashboard)
    @Query("SELECT SUM(b.nattotalAmount) FROM NatBooking b WHERE b.natstatus = 'COMPLETED'")
    BigDecimal calculateTotalRevenue();
}