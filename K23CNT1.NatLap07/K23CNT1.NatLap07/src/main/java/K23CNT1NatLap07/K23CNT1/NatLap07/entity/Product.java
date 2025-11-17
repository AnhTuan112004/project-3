package K23CNT1NatLap07.K23CNT1.NatLap07.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
// Bỏ import không cần thiết: import org.springframework.context.annotation.Primary;

@Entity
@Table (name = "products")
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // SỬA TẠI ĐÂY: Ánh xạ 'name' sang 'product_name' trong DB
    @Column(name = "product_name")
    String name;

    @Column(name = "image_url") // Nên thêm để ánh xạ chính xác
    String imageUrl;

    Integer quantity;
    Double price;
    String content;
    Boolean status;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    Category category;
}