package K23CNT1.natProject3.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "nat_reviews") // 1. Tên bảng mới
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nat_review_id") // 2. Khóa chính
    private Long id;

    @Column(name = "nat_rating") // 3. Điểm đánh giá (1-5)
    private Integer rating; // Dùng Integer thay int để tránh lỗi null safe

    @Column(name = "nat_comment", columnDefinition = "TEXT") // 4. Nội dung bình luận
    private String comment;

    @Column(name = "nat_created_at") // 5. Thời gian đánh giá
    private LocalDateTime createdAt;

    // Quan hệ N-1 với Booking (Đánh giá cho đơn nào)
    @ManyToOne
    @JoinColumn(name = "nat_booking_id") // 6. Khóa ngoại
    private Booking booking;

    // Quan hệ N-1 với User (Ai đánh giá)
    @ManyToOne
    @JoinColumn(name = "nat_user_id") // 7. Khóa ngoại
    private User user;

    // Tự động gán thời gian hiện tại
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}