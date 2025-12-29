package K23CNT1.natProject3.controller;

import K23CNT1.natProject3.dto.ChatMessageDTO;
import K23CNT1.natProject3.entity.ChatMessage;
import K23CNT1.natProject3.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
public class ChatApiController {

    @Autowired
    private ChatService chatService;

    // API cho Admin (Đã làm ở bước trước)
    @GetMapping("/history")
    public List<ChatMessage> getConversation(@RequestParam String username) {
        return chatService.getConversationWithUser(username);
    }

    /**
     * [MỚI] API cho User tự lấy lịch sử của mình
     * URL: /api/chat/my-history
     */
    @GetMapping("/my-history")
    public List<ChatMessageDTO> getMyHistory(Principal principal) {
        // 1. Nếu chưa đăng nhập -> Trả về rỗng (Khách vãng lai F5 sẽ mất chat)
        if (principal == null) {
            return Collections.emptyList();
        }

        String username = principal.getName();

        // 2. Lấy dữ liệu từ DB
        List<ChatMessage> history = chatService.getConversationWithUser(username);

        // 3. Convert sang DTO để Frontend dễ hiển thị (đặc biệt là format giờ)
        return history.stream().map(msg -> {
            return ChatMessageDTO.builder()
                    .sender(msg.getSender())
                    .recipient(msg.getRecipient())
                    .content(msg.getContent())
                    // Chuyển LocalDateTime sang chuỗi HH:mm
                    .timestamp(msg.getTimestamp().format(DateTimeFormatter.ofPattern("HH:mm")))
                    .type(msg.getType().equals("CHAT") ? ChatMessageDTO.MessageType.CHAT : ChatMessageDTO.MessageType.CHAT)
                    .build();
        }).collect(Collectors.toList());
    }
}