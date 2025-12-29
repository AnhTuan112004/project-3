package K23CNT1.natProject3.service;

import K23CNT1.natProject3.entity.ChatMessage;
import K23CNT1.natProject3.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatRepo;

    /**
     * Lưu tin nhắn vào database
     */
    @Transactional
    public ChatMessage saveMessage(ChatMessage message) {
        // Đảm bảo thời gian luôn có
        if (message.getTimestamp() == null) {
            message.setTimestamp(LocalDateTime.now());
        }

        // (Tùy chọn) Gán mặc định type là CHAT nếu thiếu
        if (message.getType() == null) {
            message.setType("CHAT");
        }

        return chatRepo.save(message);
    }

    /**
     * Lấy toàn bộ lịch sử (Dùng cho quản trị tổng hợp hoặc debug)
     */
    public List<ChatMessage> getChatHistory() {
        return chatRepo.findAllByOrderByTimestampAsc();
    }

    /**
     * Lấy hội thoại giữa khách hàng cụ thể và Admin
     * @param username Tên của khách hàng
     */
    public List<ChatMessage> getConversationWithUser(String username) {
        if (username == null || username.trim().isEmpty()) {
            return Collections.emptyList();
        }
        // Gọi custom query đã định nghĩa trong Repository
        return chatRepo.findConversation("ADMIN", username);
    }

    /**
     * Lấy danh sách những người đã từng nhắn tin cho Admin
     * Giúp Admin hiển thị danh sách các "Chat Tab" bên trái
     */
    public List<String> getRecentChatUsers() {
        // Lấy danh sách những người gửi tin đến "ADMIN"
        List<String> senders = chatRepo.findDistinctSendersToAdmin("ADMIN");

        // Loại bỏ chính Admin ra khỏi danh sách (trường hợp Admin tự chat với mình để test)
        senders.remove("ADMIN");

        return senders;
    }
}