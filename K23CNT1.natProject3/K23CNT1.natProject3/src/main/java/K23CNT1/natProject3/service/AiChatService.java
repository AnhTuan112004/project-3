package K23CNT1.natProject3.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
public class AiChatService {

    // Giả lập AI trả lời (Có thể thay bằng gọi API ChatGPT thật)
    @Async // Chạy bất đồng bộ để không làm đơ server
    public CompletableFuture<String> getAiResponse(String userMessage) {
        String msg = userMessage.toLowerCase();
        String reply;

        // Logic AI đơn giản
        if (msg.contains("giá") || msg.contains("bao nhiêu")) {
            reply = "Chào bạn, giá phòng thu bên mình từ 200k/h đến 500k/h tùy phòng ạ.";
        } else if (msg.contains("địa chỉ") || msg.contains("ở đâu")) {
            reply = "Địa chỉ: Số 10, Ngõ 123 Xuân Thủy, Cầu Giấy, Hà Nội.";
        } else if (msg.contains("đặt lịch")) {
            reply = "Bạn có thể đặt lịch trực tiếp trên website ở mục Trang chủ nhé!";
        } else if (msg.contains("xin chào") || msg.contains("hello")) {
            reply = "Chào bạn! Mình là trợ lý ảo NAT Studio. Mình có thể giúp gì cho bạn?";
        } else {
            reply = "Cảm ơn bạn đã nhắn tin. Admin sẽ phản hồi sớm nhất có thể ạ!";
        }

        // Giả lập độ trễ suy nghĩ của AI (1 giây)
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        return CompletableFuture.completedFuture(reply);
    }
}