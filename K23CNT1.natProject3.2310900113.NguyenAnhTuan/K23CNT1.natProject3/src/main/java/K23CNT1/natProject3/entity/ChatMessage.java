package K23CNT1.natProject3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "nat_chat_messages", indexes = {
        @Index(name = "idx_sender", columnList = "nat_sender"),
        @Index(name = "idx_recipient", columnList = "nat_recipient"),
        @Index(name = "idx_timestamp", columnList = "nat_timestamp")
})
@Data
@AllArgsConstructor
@NoArgsConstructor // Cần thiết cho Hibernate
@Builder           // Hỗ trợ tạo object nhanh: ChatMessage.builder()...build()
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nat_msg_id")
    private Long id;

    @Column(name = "nat_sender", nullable = false)
    private String sender;

    @Column(name = "nat_recipient", nullable = false)
    private String recipient;

    @Column(name = "nat_content", columnDefinition = "TEXT", nullable = false)
    private String content;

    // [BỔ SUNG QUAN TRỌNG] Loại tin nhắn (CHAT, JOIN, LEAVE)
    // Để Controller có thể gọi msg.setType("CHAT")
    @Column(name = "nat_type")
    private String type;

    @Column(name = "nat_timestamp")
    private LocalDateTime timestamp;

    /**
     * Tự động gán thời gian hiện tại trước khi lưu vào DB nếu thiếu
     */
    @PrePersist
    protected void onCreate() {
        if (this.timestamp == null) {
            this.timestamp = LocalDateTime.now();
        }
        if (this.type == null) {
            this.type = "CHAT"; // Mặc định là tin nhắn thường
        }
    }
}