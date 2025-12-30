package K23CNT1.natProject3.controller;

import K23CNT1.natProject3.dto.ChatMessageDTO;
import K23CNT1.natProject3.entity.ChatMessage;
import K23CNT1.natProject3.service.AiChatService; // [MỚI] Import Service AI
import K23CNT1.natProject3.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatService chatService;

    @Autowired
    private AiChatService aiService; // [MỚI] Tiêm Service AI vào

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 1. KHÁCH HÀNG GỬI TIN NHẮN CHO ADMIN
     * Client JS gọi: stompClient.send("/app/chat.sendToAdmin", ...)
     */
    @MessageMapping("/chat.sendToAdmin")
    public void sendToAdmin(@Payload ChatMessageDTO messageDto, Principal principal) {
        try {
            // A. Xác định người gửi an toàn
            String safeSender = (principal != null) ? principal.getName() : messageDto.getSender();
            messageDto.setSender(safeSender);

            // Gán người nhận mặc định là ADMIN
            messageDto.setRecipient("ADMIN");

            // Format giờ hiển thị
            String timeStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
            messageDto.setTimestamp(timeStr);

            // B. Lưu tin nhắn của KHÁCH vào Database
            saveToDatabase(messageDto);

            // C. Gửi đến Admin (để Admin thấy khách vừa nhắn gì)
            messagingTemplate.convertAndSend("/topic/admin", messageDto);

            // D. Gửi xác nhận lại cho Khách (Feedback)
            messagingTemplate.convertAndSendToUser(
                    safeSender,
                    "/queue/reply",
                    messageDto
            );

            logger.info("User [{}] sent message to ADMIN", safeSender);

            // E. [MỚI] KÍCH HOẠT AI TRẢ LỜI TỰ ĐỘNG
            // Gọi hàm xử lý AI (Hàm này chạy ngầm, không làm đơ server)
            callAiBot(safeSender, messageDto.getContent());

        } catch (Exception e) {
            logger.error("Lỗi khi gửi tin tới Admin", e);
        }
    }

    /**
     * 2. ADMIN TRẢ LỜI LẠI KHÁCH HÀNG
     * Admin JS gọi: stompClient.send("/app/chat.reply", ...)
     */
    @MessageMapping("/chat.reply")
    public void replyToUser(@Payload ChatMessageDTO messageDto, Principal principal) {
        try {
            // A. Xác thực người gửi là ADMIN
            String adminName = (principal != null) ? principal.getName() : "ADMIN";
            messageDto.setSender(adminName);

            // Format giờ
            String timeStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
            messageDto.setTimestamp(timeStr);

            // B. Lưu tin nhắn ADMIN vào Database
            saveToDatabase(messageDto);

            // C. Gửi ĐÍCH DANH cho khách hàng
            messagingTemplate.convertAndSendToUser(
                    messageDto.getRecipient(),
                    "/queue/reply",
                    messageDto
            );

            // D. Gửi lại cho Admin (để hiện lên chatbox của Admin)
            messagingTemplate.convertAndSendToUser(
                    adminName,
                    "/queue/reply", // Admin cũng nghe ở kênh này để cập nhật UI
                    messageDto
            );

            logger.info("ADMIN replied to User [{}]", messageDto.getRecipient());

        } catch (Exception e) {
            logger.error("Lỗi khi Admin trả lời", e);
        }
    }

    /**
     * [MỚI] Hàm xử lý AI trả lời tự động
     * @param userRecipient: Tên người nhận (Khách hàng)
     * @param userMessage: Nội dung khách vừa nhắn
     */
    private void callAiBot(String userRecipient, String userMessage) {
        // Gọi Service AI (Chạy bất đồng bộ để Server không phải chờ)
        aiService.getAiResponse(userMessage).thenAccept(aiReplyContent -> {

            // 1. Tạo DTO tin nhắn của AI
            ChatMessageDTO aiMsg = ChatMessageDTO.builder()
                    .sender("Trợ lý ảo")       // Tên hiển thị của Bot
                    .recipient(userRecipient)   // Gửi cho khách
                    .content(aiReplyContent)    // Nội dung AI nghĩ ra
                    .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))
                    .type(ChatMessageDTO.MessageType.CHAT)
                    .build();

            // 2. Lưu tin nhắn AI vào Database (Để lịch sử chat liền mạch)
            saveToDatabase(aiMsg);

            // 3. Gửi cho Khách hàng (User thấy Bot trả lời)
            messagingTemplate.convertAndSendToUser(
                    userRecipient,
                    "/queue/reply",
                    aiMsg
            );

            // 4. Gửi cho Admin (Admin thấy Bot đã trả lời thay mình)
            messagingTemplate.convertAndSend("/topic/admin", aiMsg);

            logger.info("AI Bot replied to [{}]", userRecipient);
        });
    }

    /**
     * Hàm phụ trợ: Lưu tin nhắn từ DTO sang Entity
     */
    private void saveToDatabase(ChatMessageDTO dto) {
        try {
            ChatMessage msg = new ChatMessage();
            msg.setSender(dto.getSender());
            msg.setRecipient(dto.getRecipient());
            msg.setContent(dto.getContent());
            msg.setType("CHAT");
            msg.setTimestamp(LocalDateTime.now());

            chatService.saveMessage(msg);
        } catch (Exception e) {
            logger.error("Lỗi lưu DB: ", e);
        }
    }
}