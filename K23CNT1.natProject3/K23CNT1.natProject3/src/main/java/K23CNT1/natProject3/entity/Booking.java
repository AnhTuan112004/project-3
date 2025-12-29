package K23CNT1.natProject3.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "nat_bookings") // 1. Tên bảng mới
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nat_booking_id") // 2. Khóa chính
    private Long id;

    @Column(name = "nat_start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "nat_end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "nat_total_price")
    private BigDecimal totalPrice;

    @Column(name = "nat_deposit")
    private BigDecimal deposit;

    @Column(name = "nat_status")
    private String status; // PENDING, CONFIRMED, COMPLETED, CANCELLED, REJECTED

    @Column(name = "nat_created_at")
    private LocalDateTime createdAt;

    // ================= QUAN HỆ (FOREIGN KEYS) =================

    // Quan hệ với Khách hàng (nat_users)
    @ManyToOne
    @JoinColumn(name = "nat_user_id") // 3. Khóa ngoại trỏ về User
    private User user;

    // Quan hệ với Phòng thu (nat_studios)
    @ManyToOne
    @JoinColumn(name = "nat_studio_id") // 4. Khóa ngoại trỏ về Studio
    private Studio studio;

    // Quan hệ với Kỹ thuật viên (nat_users)
    @ManyToOne
    @JoinColumn(name = "nat_technician_id") // 5. Khóa ngoại trỏ về User (Technician)
    private User technician;

    // ================= DANH SÁCH LIÊN KẾT (ONE-TO-MANY) =================

    // Lưu ý: Các Entity con (BookingEquipment, Payment, Review)
    // cũng phải có trường "private Booking booking;" để mappedBy hoạt động.

    // Danh sách thiết bị thuê kèm
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<BookingEquipment> bookingEquipments;

    // Danh sách lịch sử thanh toán
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Payment> payments;

    // Danh sách đánh giá
    @OneToMany(mappedBy = "booking")
    @ToString.Exclude
    private List<Review> reviews;

    // Tự động gán thời gian tạo khi lưu mới
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}