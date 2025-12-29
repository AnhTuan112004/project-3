package K23CNT1.natProject3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 1. Đăng ký endpoint "/ws" để Client kết nối
        // setAllowedOriginPatterns("*"): Cho phép mọi nguồn (localhost, domain khác) kết nối để tránh lỗi CORS
        // withSockJS(): Kích hoạt chế độ fallback nếu trình duyệt không hỗ trợ WebSocket gốc
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 2. Cấu hình Prefix cho tin nhắn Client GỬI LÊN Server
        // Ví dụ: Client gửi tới "/app/chat.sendMessage"
        registry.setApplicationDestinationPrefixes("/app");

        // 3. Cấu hình Broker cho tin nhắn Client NHẬN VỀ (Subscribe)
        // "/topic": Thường dùng cho thông báo chung (Broadcast) - VD: Phòng chat công khai
        // "/queue": Thường dùng cho tin nhắn riêng tư (P2P) - VD: Chat Admin với Khách
        registry.enableSimpleBroker("/topic", "/queue");

        // 4. [QUAN TRỌNG] Cấu hình Prefix cho tin nhắn riêng tư (User-specific)
        // Giúp gửi tin nhắn kiểu: convertAndSendToUser("username", "/queue/reply", msg)
        registry.setUserDestinationPrefix("/user");
    }
}