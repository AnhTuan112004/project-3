package K23CNT1.natProject3.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "natrooms")
@Data @NoArgsConstructor @AllArgsConstructor
public class NatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natid;

    private String natname;
    private BigDecimal natprice; // Giá theo giờ
    private String natstatus;    // ACTIVE, MAINTENANCE
    private String natimage;     // Link ảnh

    // Liên kết với bảng Loại Phòng
    @ManyToOne
    @JoinColumn(name = "nattype_id")
    private NatRoomType natRoomType;
}