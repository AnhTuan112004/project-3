var stompClient = null;
var currentRecipient = null;

// 1. Kết nối WebSocket với quyền Admin
function connectAdmin() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function () {
        // Đăng ký nhận tin nhắn mới từ TẤT CẢ khách hàng
        stompClient.subscribe('/topic/admin', function (payload) {
            var msg = JSON.parse(payload.body);
            // Nếu đang chat với đúng người này thì hiện tin nhắn lên
            if (msg.sender === currentRecipient) {
                appendMessage(msg.sender, msg.content, 'user');
            } else {
                // Nếu không, thông báo có tin mới (bôi đậm user bên trái)
                alert("Tin nhắn mới từ: " + msg.sender);
            }
        });
    });
}

// 2. Load lịch sử chat
function loadChat(username) {
    currentRecipient = username;
    document.getElementById('currentChatUser').innerText = username;
    document.getElementById('adminMsgInput').disabled = false;
    document.getElementById('adminSendBtn').disabled = false;

    // Highlight user đang chọn
    document.querySelectorAll('.user-item').forEach(el => el.classList.remove('active'));
    var userEl = document.getElementById('user-' + username);
    if(userEl) userEl.classList.add('active');

    // Gọi API lấy lịch sử
    fetch('/api/chat/history?username=' + username)
        .then(res => res.json())
        .then(data => {
            var chatArea = document.getElementById('adminChatArea');
            chatArea.innerHTML = ''; // Xóa cũ
            data.forEach(msg => {
                var type = (msg.sender === 'ADMIN') ? 'admin' : 'user';
                appendMessage(msg.sender, msg.content, type);
            });
            chatArea.scrollTop = chatArea.scrollHeight;
        });
}

// 3. Admin trả lời
function sendAdminReply() {
    var input = document.getElementById('adminMsgInput');
    var content = input.value.trim();
    if (content && currentRecipient) {
        var msg = {
            sender: 'ADMIN',
            recipient: currentRecipient,
            content: content,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.reply", {}, JSON.stringify(msg));
        appendMessage('ADMIN', content, 'admin'); // Hiện lên giao diện mình
        input.value = '';
    }
}

// Helper: Vẽ tin nhắn lên màn hình
function appendMessage(sender, content, type) {
    var chatArea = document.getElementById('adminChatArea');
    var div = document.createElement('div');
    div.className = 'msg-' + type;
    div.innerHTML = `<span>${content}</span>`;
    chatArea.appendChild(div);
    chatArea.scrollTop = chatArea.scrollHeight;
}

// Chạy kết nối khi vào trang
document.addEventListener('DOMContentLoaded', connectAdmin);