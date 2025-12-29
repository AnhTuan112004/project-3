package K23CNT1.natProject3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageDTO {

    private String sender;      // Tên người gửi (Ví dụ: "Nguyen Van A" hoặc "ADMIN")

    private String recipient;   // Người nhận (Ví dụ: "ADMIN" hoặc "username_khach")

    private String content;     // Nội dung tin nhắn

    private String timestamp;   // Lưu chuỗi định dạng "HH:mm" để hiển thị ngay lên UI

    // [QUAN TRỌNG] Thêm @Builder.Default:
    // Để khi bạn dùng ChatMessageDTO.builder().build() mà quên setType,
    // nó sẽ tự động lấy giá trị mặc định là CHAT thay vì null.
    @Builder.Default
    private MessageType type = MessageType.CHAT;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}