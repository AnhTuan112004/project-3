package K23CNT1.natProject3.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "natreviews")
@Data @NoArgsConstructor @AllArgsConstructor
public class NatReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natid;

    // Số sao đánh giá (Thường là 1 đến 5)
    @Column(nullable = false)
    private Integer natrating;

    // Nội dung bình luận
    // Sử dụng columnDefinition = "TEXT" để khách viết dài thoải mái không bị giới hạn 255 ký tự
    @Column(columnDefinition = "TEXT")
    private String natcomment;

    // Thời gian tạo đánh giá (Mặc định lấy giờ hiện tại)
    private LocalDateTime natcreatedAt = LocalDateTime.now();

    // --- CÁC MỐI QUAN HỆ (RELATIONSHIPS) ---

    // 1. Ai là người viết đánh giá? (Liên kết với bảng User)
    @ManyToOne
    @JoinColumn(name = "natuser_id")
    private NatUser natUser;

    // 2. Đánh giá cho phòng nào? (Liên kết với bảng Room)
    // Mục đích: Để khi vào trang chi tiết phòng, ta dễ dàng `select * from reviews where room_id = ?`
    @ManyToOne
    @JoinColumn(name = "natroom_id")
    private NatRoom natRoom;

    // 3. Đánh giá thuộc về đơn hàng nào? (Liên kết với bảng Booking)
    // Quan hệ OneToOne: 1 Đơn hàng -> Chỉ có 1 Đánh giá.
    // Giúp hệ thống kiểm soát: Khách phải đặt và dùng xong mới được review.
    @OneToOne
    @JoinColumn(name = "natbooking_id")
    private NatBooking natBooking;
}