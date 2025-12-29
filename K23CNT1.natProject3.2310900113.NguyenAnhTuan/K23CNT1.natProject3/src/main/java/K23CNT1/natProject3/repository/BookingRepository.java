package K23CNT1.natProject3.repository;

import K23CNT1.natProject3.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserIdOrderByStartTimeDesc(Long userId);

    List<Booking> findByUserId(Long userId);

    List<Booking> findAllByOrderByIdDesc();

    List<Booking> findByStudioId(Long studioId);

    // Kiểm tra trùng lịch: Sử dụng các field đã ánh xạ nat_
    @Query("SELECT b FROM Booking b WHERE b.studio.id = :studioId " +
            "AND b.status NOT IN ('CANCELLED', 'REJECTED') " +
            "AND ((b.startTime < :endTime) AND (b.endTime > :startTime))")
    List<Booking> findConflictingBookings(@Param("studioId") Long studioId,
                                          @Param("startTime") LocalDateTime startTime,
                                          @Param("endTime") LocalDateTime endTime);

    // Thống kê đơn PENDING cho Dashboard
    long countByStatus(String status);

    // Tính doanh thu tháng: Sử dụng field totalPrice (nat_total_price) và endTime (nat_end_time)
    @Query("SELECT SUM(b.totalPrice) FROM Booking b " +
            "WHERE b.status = 'COMPLETED' " +
            "AND MONTH(b.endTime) = :month " +
            "AND YEAR(b.endTime) = :year")
    BigDecimal sumRevenueByMonth(@Param("month") int month, @Param("year") int year);
}