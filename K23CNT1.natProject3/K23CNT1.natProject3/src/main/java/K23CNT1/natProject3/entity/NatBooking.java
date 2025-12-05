package K23CNT1.natProject3.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "natbookings")
@Data @NoArgsConstructor @AllArgsConstructor
public class NatBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natid;

    private LocalDateTime natstartTime;
    private LocalDateTime natendTime;
    private BigDecimal nattotalAmount;

    // PENDING, DEPOSITED, COMPLETED, CANCELLED
    private String natstatus;

    private LocalDateTime natcreatedAt = LocalDateTime.now();

    // Khách hàng nào đặt?
    @ManyToOne
    @JoinColumn(name = "natuser_id")
    private NatUser natUser;

    // Đặt phòng nào?
    @ManyToOne
    @JoinColumn(name = "natroom_id")
    private NatRoom natRoom;

    // Danh sách thiết bị thuê kèm (Quan hệ 1-N)
    // Cascade ALL: Xóa booking thì xóa luôn chi tiết thiết bị kèm theo
    @OneToMany(mappedBy = "natBooking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NatBookingEquipment> natBookingEquipments;
    // Trong file NatBooking.java

    @OneToOne(mappedBy = "natBooking")
    private NatReview natReview;
}