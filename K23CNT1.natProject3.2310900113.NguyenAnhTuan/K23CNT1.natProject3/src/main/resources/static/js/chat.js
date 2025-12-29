/* src/main/resources/static/js/chat.js */

var stompClient = null;
var currentUser = null;
var currentRole = null;

/**
 * 1. Kết nối tới WebSocket
 * @param userFullName: Tên hiển thị (Lấy từ Thymeleaf)
 * @param role: Vai trò (USER/ADMIN)
 */
function connect(userFullName, role) {
    // Nếu không có tên (Khách), đặt tên mặc định để giao diện JS nhận biết
    currentUser = userFullName || "Khách vãng lai";
    currentRole = role || "USER";

    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    // Bật log để debug khi phát triển (nên tắt khi production)
    // stompClient.debug = null;

    stompClient.connect({}, function (frame) {
        console.log('Đã kết nối WebSocket: ' + frame);
        updateStatusUI(true); // Đèn xanh

        // --- ĐĂNG KÝ KÊNH NHẬN TIN ---

        // A. Kênh riêng tư: Nhận tin nhắn phản hồi từ Admin hoặc Server feedback
        // Endpoint này tương ứng với messagingTemplate.convertAndSendToUser(..., "/queue/reply", ...)
        stompClient.subscribe('/user/queue/reply', function (payload) {
            var message = JSON.parse(payload.body);
            showMessage(message);
        });

        // B. Kênh Admin: Chỉ Admin mới đăng ký kênh này để nghe "tiếng động" từ khách
        if (currentRole === 'ADMIN') {
            console.log("Đang lắng nghe kênh Admin...");
            stompClient.subscribe('/topic/admin', function (payload) {
                var message = JSON.parse(payload.body);
                // Lưu ý: Admin có thể cần UI riêng để hiển thị danh sách người chat
                // Ở đây tạm thời hiển thị chung vào khung chat
                showMessage(message);
            });
        }

    }, function(error) {
        console.error("Mất kết nối WebSocket: " + error);
        updateStatusUI(false); // Đèn đỏ

        // Tự động kết nối lại sau 5 giây
        setTimeout(() => connect(currentUser, currentRole), 5000);
    });
}

/**
 * 2. Gửi tin nhắn
 */
function sendMessage() {
    var messageInput = document.getElementById('messageInput');
    var content = messageInput.value.trim();

    if(content && stompClient && stompClient.connected) {
        var chatMessage = {
            sender: currentUser,    // Tên người gửi hiện tại
            recipient: "ADMIN",     // Mặc định gửi cho Admin
            content: content,
            type: 'CHAT'            // Loại tin nhắn
        };

        // Gửi tới Controller: @MessageMapping("/chat.sendToAdmin")
        stompClient.send("/app/chat.sendToAdmin", {}, JSON.stringify(chatMessage));

        // Xóa ô nhập liệu
        messageInput.value = '';

        // Lưu ý: Không gọi showMessage() ở đây.
        // Chúng ta đợi Server gửi lại tin nhắn qua kênh /user/queue/reply để hiển thị.
        // Điều này đảm bảo tin nhắn ĐÃ được Server nhận và xử lý thành công.

    } else if (!stompClient || !stompClient.connected) {
        alert("Mất kết nối máy chủ. Vui lòng đợi trong giây lát...");
    }
}

/**
 * 3. Hiển thị tin nhắn lên giao diện
 */
function showMessage(message) {
    var messageArea = document.getElementById('chatMessages');
    if (!messageArea) return;

    // A. Xử lý tin nhắn hệ thống (JOIN/LEAVE) - Nếu có
    if (message.type === 'JOIN' || message.type === 'LEAVE') {
        var systemDiv = document.createElement('div');
        systemDiv.classList.add('text-center', 'small', 'text-muted', 'my-2');
        systemDiv.innerText = message.content;
        messageArea.appendChild(systemDiv);
        return;
    }

    // B. Xử lý tin nhắn CHAT thông thường
    var wrapper = document.createElement('div');
    wrapper.classList.add('message-wrapper');

    // Kiểm tra xem tin nhắn này có phải do chính mình gửi không
    // So sánh tên người gửi trong tin nhắn với currentUser
    var isSelf = (message.sender === currentUser);

    // Thêm class CSS để căn trái (receiver) hoặc căn phải (sender)
    wrapper.classList.add(isSelf ? 'sender' : 'receiver');

    // Nội dung bong bóng chat
    var contentDiv = document.createElement('div');
    contentDiv.classList.add('message-content');

    // Hiển thị tên người gửi (nếu là người khác gửi cho mình)
    if (!isSelf) {
        var senderName = document.createElement('strong');
        senderName.className = "d-block small mb-1 text-primary";
        senderName.style.fontSize = "0.75rem";
        senderName.textContent = message.sender;
        contentDiv.appendChild(senderName);
    }

    // Nội dung tin nhắn (Chống XSS bằng createTextNode)
    var textNode = document.createTextNode(message.content);
    contentDiv.appendChild(textNode);

    // Thời gian
    var timeSmall = document.createElement('small');
    timeSmall.classList.add('message-time');
    // Ưu tiên lấy timestamp từ Server (dạng HH:mm), nếu không có thì lấy giờ Client
    timeSmall.innerText = message.timestamp || new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });

    // Ghép các phần tử vào Wrapper
    wrapper.appendChild(contentDiv);
    wrapper.appendChild(timeSmall);

    // Đưa vào khung chat
    messageArea.appendChild(wrapper);

    // Tự động cuộn xuống dòng cuối cùng
    messageArea.scrollTo({
        top: messageArea.scrollHeight,
        behavior: 'smooth'
    });
}

/**
 * 4. Các hàm tiện ích giao diện
 */
function toggleChat() {
    var chatBox = document.getElementById('chatContainer');
    if (chatBox) {
        chatBox.classList.toggle('active');
        // Auto focus khi mở
        if (chatBox.classList.contains('active')) {
            setTimeout(() => document.getElementById('messageInput').focus(), 300);
        }
    }
}

function updateStatusUI(isConnected) {
    var dot = document.getElementById('chat-status-dot');
    var text = document.getElementById('connection-text');
    var input = document.getElementById('messageInput');
    var btn = document.getElementById('sendBtn');

    if (dot && text) {
        if (isConnected) {
            dot.className = 'status-dot online';
            text.innerText = 'Trực tuyến';
            if(input) input.disabled = false;
            if(btn) btn.disabled = false;
        } else {
            dot.className = 'status-dot offline';
            text.innerText = 'Mất kết nối...';
            if(input) input.disabled = true;
            if(btn) btn.disabled = true;
        }
    }
}
function connect(userFullName, role) {
    currentUser = userFullName || "Khách vãng lai";
    currentRole = role || "USER";

    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    // stompClient.debug = null; // Tắt log nếu muốn

    stompClient.connect({}, function (frame) {
        console.log('Đã kết nối WebSocket');
        updateStatusUI(true);

        // --- 1. ĐĂNG KÝ KÊNH NHẬN TIN (Giữ nguyên) ---
        stompClient.subscribe('/user/queue/reply', function (payload) {
            var message = JSON.parse(payload.body);
            showMessage(message);
        });

        if (currentRole === 'ADMIN') {
            stompClient.subscribe('/topic/admin', function (payload) {
                showMessage(JSON.parse(payload.body));
            });
        }

        // --- [MỚI] 2. TẢI LỊCH SỬ CHAT CŨ ---
        // Chỉ tải nếu là User đã đăng nhập (Khách vãng lai không có lịch sử lưu theo phiên)
        if (currentRole === 'USER' && currentUser !== "Khách vãng lai") {
            loadChatHistory();
        }

    }, function(error) {
        console.error("Lỗi kết nối: " + error);
        updateStatusUI(false);
        setTimeout(() => connect(currentUser, currentRole), 5000);
    });
}

/**
 * [MỚI] Hàm gọi API lấy lịch sử cũ
 */
function loadChatHistory() {
    fetch('/api/chat/my-history')
        .then(response => {
            if (!response.ok) throw new Error("Không tải được lịch sử");
            return response.json();
        })
        .then(data => {
            // Xóa nội dung cũ (nếu có) để tránh trùng lặp
            var messageArea = document.getElementById('chatMessages');
            if(messageArea) messageArea.innerHTML = '';

            // Hiển thị từng tin nhắn cũ
            data.forEach(msg => {
                showMessage(msg);
            });

            // Cuộn xuống cuối cùng
            if(messageArea) messageArea.scrollTop = messageArea.scrollHeight;
        })
        .catch(error => console.log("Chưa có lịch sử chat hoặc lỗi: ", error));
}

// Lắng nghe sự kiện nhấn Enter để gửi tin
document.addEventListener('DOMContentLoaded', function() {
    var input = document.getElementById('messageInput');
    if (input) {
        input.addEventListener('keypress', function (e) {
            if (e.key === 'Enter') {
                sendMessage();
            }
        });
    }
});