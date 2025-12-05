package K23CNT1.natProject3.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "natpayments")
@Data @NoArgsConstructor @AllArgsConstructor
public class NatPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natid;

    private BigDecimal natamount;
    private String natpaymentMethod; // VNPAY, CASH, TRANSFER
    private LocalDateTime natpaymentTime = LocalDateTime.now();
    private String nattype; // DEPOSIT (Cọc), FINAL (Thanh toán hết)

    // Của đơn hàng nào?
    @ManyToOne
    @JoinColumn(name = "natbooking_id")
    private NatBooking natBooking;
}