package K23CNT1.natProject3.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "nat_payments") // 1. Tên bảng mới
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nat_payment_id") // 2. Khóa chính
    private Long id;

    @Column(name = "nat_amount") // 3. Số tiền
    private BigDecimal amount;

    @Column(name = "nat_payment_method") // 4. Phương thức (VNPAY, CASH...)
    private String paymentMethod;

    @Column(name = "nat_payment_date") // 5. Ngày thanh toán
    private LocalDateTime paymentDate;

    @Column(name = "nat_transaction_code") // 6. Mã giao dịch
    private String transactionCode;

    @Column(name = "nat_status") // 7. Trạng thái (SUCCESS, FAILED)
    private String status;

    // Quan hệ N-1 với Booking
    @ManyToOne
    @JoinColumn(name = "nat_booking_id") // 8. Khóa ngoại trỏ về bảng Booking
    private Booking booking;

    // Tự động gán thời gian hiện tại nếu chưa có
    @PrePersist
    protected void onCreate() {
        if (paymentDate == null) {
            paymentDate = LocalDateTime.now();
        }
    }
}