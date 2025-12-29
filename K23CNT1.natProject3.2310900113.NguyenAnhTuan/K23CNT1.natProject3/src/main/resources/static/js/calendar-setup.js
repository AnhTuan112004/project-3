/**
 * Khởi tạo FullCalendar
 * @param {string} studioId - ID của phòng thu để lấy lịch
 */
function initCalendar(studioId) {
    var calendarEl = document.getElementById('calendar');

    // Hàm tiện ích: Chuyển đổi Date object sang chuỗi 'YYYY-MM-DDTHH:mm' theo giờ địa phương
    // Giúp input type="datetime-local" hiểu được
    function toLocalISOString(date) {
        var tzOffset = (date.getTimezoneOffset() * 60000); // Lấy độ lệch múi giờ theo phút
        var localISOTime = (new Date(date - tzOffset)).toISOString().slice(0, 16);
        return localISOTime;
    }

    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'timeGridWeek',
        locale: 'vi', // Chuyển sang tiếng Việt (cần import file locale vi.js hoặc dùng CDN)
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'timeGridWeek,timeGridDay'
        },
        height: 'auto',         // Chiều cao tự động vừa nội dung
        contentHeight: 600,     // Chiều cao tối thiểu
        allDaySlot: false,      // Ẩn dòng "Cả ngày"
        slotMinTime: "08:00:00",
        slotMaxTime: "23:00:00",
        nowIndicator: true,     // Hiện vạch đỏ chỉ giờ hiện tại

        // CẤU HÌNH KÉO THẢ CHỌN GIỜ (QUAN TRỌNG)
        selectable: true,       // Cho phép kéo chuột chọn vùng
        selectMirror: true,     // Hiển thị vùng đang chọn
        selectOverlap: false,   // Không cho chọn đè lên lịch đã có

        // Gọi API lấy dữ liệu
        events: '/api/calendar/events?studioId=' + studioId,

        // Xử lý khi người dùng KÉO CHUỘT chọn giờ (Thay thế dateClick)
        select: function(info) {
            var startStr = toLocalISOString(info.start);
            var endStr = toLocalISOString(info.end);

            // 1. Điền vào Form
            var startTimeInput = document.getElementById('startTime');
            var endTimeInput = document.getElementById('endTime');

            if(startTimeInput) startTimeInput.value = startStr;
            if(endTimeInput) endTimeInput.value = endStr;

            // 2. Tính tổng tiền tạm tính (Optional - UX)
            // Nếu bạn có hàm tính tiền bên ngoài, hãy gọi nó ở đây.
            // updateTotalPrice();

            // 3. Cuộn màn hình xuống form đặt lịch (Mobile friendly)
            if(window.innerWidth < 768 && startTimeInput) {
                startTimeInput.scrollIntoView({ behavior: 'smooth', block: 'center' });
            }

            // 4. Bỏ vùng chọn trên lịch để nhìn cho đỡ rối (hoặc giữ lại tùy ý)
            // calendar.unselect();
        },

        // Xử lý sự kiện click vào một lịch đã đặt (Optional)
        eventClick: function(info) {
            alert('Khung giờ này đã được đặt!\n' +
                  'Thời gian: ' + info.event.start.toLocaleTimeString() + ' - ' + info.event.end.toLocaleTimeString());
        }
    });

    calendar.render();
}