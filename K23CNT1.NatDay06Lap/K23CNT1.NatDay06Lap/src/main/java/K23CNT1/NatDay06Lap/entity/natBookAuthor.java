package K23CNT1.NatDay06Lap.entity;

import jakarta.persistence.*;
import lombok.*;

// Entity liên kết giữa Sách và Tác giả, mang thuộc tính vai trò
@Entity
@Table(name = "nat_book_author") // Sửa tên bảng (snake_case)
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class natBookAuthor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mối quan hệ Many-to-One với natbook
    @ManyToOne(fetch = FetchType.LAZY)
    // ✅ ĐÃ SỬA: name khóa ngoại là natbook_id, referencedColumnName là nat_id
    @JoinColumn(name = "natbook_id", referencedColumnName = "nat_id", nullable = false)
    private natbook book;

    // Mối quan hệ Many-to-One với natauthor
    @ManyToOne(fetch = FetchType.LAZY)
    // ✅ ĐÃ SỬA: name khóa ngoại là natauthor_id, referencedColumnName là nat_id
    @JoinColumn(name = "natauthor_id", referencedColumnName = "nat_id", nullable = false)
    private natauthor author;

    // Thuộc tính bổ sung
    @Column(name = "is_primary_author")
    private Boolean isPrimaryAuthor = false;
}