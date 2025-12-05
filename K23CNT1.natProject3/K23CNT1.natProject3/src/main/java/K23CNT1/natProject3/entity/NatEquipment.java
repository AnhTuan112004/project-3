package K23CNT1.natProject3.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "natequipments")
@Data @NoArgsConstructor @AllArgsConstructor
public class NatEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natid;

    private String natname;      // Tên thiết bị (Guitar, Mic...)
    private BigDecimal natprice; // Giá thuê/giờ
    private Integer natstock;    // Số lượng tồn kho
}