package K23CNT1.natProject3.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "nat_posts") // 1. Tên bảng mới
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nat_post_id") // 2. Khóa chính
    private Long id;

    @Column(name = "nat_title") // 3. Tiêu đề bài viết
    private String title;

    @Column(name = "nat_content", columnDefinition = "TEXT") // 4. Nội dung
    private String content;

    @Column(name = "nat_image_url") // 5. Ảnh bìa bài viết
    private String imageUrl;

    @Column(name = "nat_created_at") // 6. Thời gian tạo
    private LocalDateTime createdAt;

    // Quan hệ N-1 với User (Tác giả)
    @ManyToOne
    @JoinColumn(name = "nat_author_id") // 7. Khóa ngoại trỏ về nat_users
    private User author;

    // Tự động gán thời gian hiện tại khi đăng bài
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}