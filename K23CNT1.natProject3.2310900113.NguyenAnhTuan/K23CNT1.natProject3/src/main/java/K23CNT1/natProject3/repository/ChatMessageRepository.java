package K23CNT1.natProject3.repository;

import K23CNT1.natProject3.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // 1. Lấy toàn bộ tin nhắn (Dùng cho debug hoặc Admin xem tất cả)
    List<ChatMessage> findAllByOrderByTimestampAsc();

    /**
     * 2. Lấy cuộc hội thoại 2 chiều giữa Admin và User
     * Logic: (Người gửi là Admin VÀ Người nhận là User) HOẶC (Người gửi là User VÀ Người nhận là Admin)
     * Sắp xếp: Tin nhắn cũ nhất lên đầu (ASC) để hiển thị từ trên xuống dưới
     */
    @Query("SELECT c FROM ChatMessage c WHERE " +
            "(c.sender = :admin AND c.recipient = :user) OR " +
            "(c.sender = :user AND c.recipient = :admin) " +
            "ORDER BY c.timestamp ASC")
    List<ChatMessage> findConversation(@Param("admin") String admin,
                                       @Param("user") String user);

    /**
     * 3. Lấy danh sách những người đã nhắn tin cho Admin (Sidebar)
     * Logic cũ: Sắp xếp theo tên (A->Z).
     * Logic mới (Khuyên dùng): Group By theo người gửi và sắp xếp theo thời gian tin nhắn mới nhất.
     * Lưu ý: Nếu database báo lỗi Group By, hãy dùng lại code cũ của bạn.
     */
    // Cách 1: Sắp xếp theo tên (An toàn nhất, ít lỗi SQL)
    @Query("SELECT DISTINCT c.sender FROM ChatMessage c WHERE c.recipient = :admin ORDER BY c.sender ASC")
    List<String> findDistinctSendersToAdmin(@Param("admin") String admin);

    /* // Cách 2: Sắp xếp theo người mới nhắn gần nhất (Nâng cao - UX tốt hơn)
    // Nếu bạn dùng MySQL 5.7+ hoặc PostgreSQL, có thể thử dòng này:
    @Query("SELECT c.sender FROM ChatMessage c WHERE c.recipient = :admin GROUP BY c.sender ORDER BY MAX(c.timestamp) DESC")
    List<String> findRecentSenders(@Param("admin") String admin);
    */
}