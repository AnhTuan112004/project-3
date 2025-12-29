package K23CNT1.natProject3.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "nat_equipments") // 1. Tên bảng mới
@Data
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nat_equipment_id") // 2. Khóa chính
    private Long id;

    @Column(name = "nat_name", nullable = false) // 3. Tên thiết bị
    private String name;

    @Column(name = "nat_description", columnDefinition = "TEXT") // 4. Mô tả
    private String description;

    @Column(name = "nat_quantity") // 5. Số lượng tồn kho
    private Integer quantity; // Nên dùng Integer thay vì int để tránh lỗi nếu null

    @Column(name = "nat_rent_price") // 6. Giá thuê
    private BigDecimal rentPrice;

    @Column(name = "nat_image_url") // 7. Ảnh thiết bị
    private String imageUrl;

    // [Feature 3] Tag gợi ý (VOCAL, DRUM, GUITAR...)
    @Column(name = "nat_category_tag") // 8. Tag phân loại
    private String categoryTag;
}