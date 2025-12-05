package K23CNT1.natProject3.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "natbooking_equipments")
@Data @NoArgsConstructor @AllArgsConstructor
public class NatBookingEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natid;

    private Integer natquantity; // Số lượng thuê
    private BigDecimal natpriceAtBooking; // Giá tại thời điểm đặt

    // Thuộc đơn hàng nào?
    @ManyToOne
    @JoinColumn(name = "natbooking_id")
    private NatBooking natBooking;

    // Là thiết bị gì?
    @ManyToOne
    @JoinColumn(name = "natequipment_id")
    private NatEquipment natEquipment;
}