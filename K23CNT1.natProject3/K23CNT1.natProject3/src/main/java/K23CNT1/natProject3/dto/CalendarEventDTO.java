package K23CNT1.natProject3.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarEventDTO {

    // Tên hiển thị trên lịch (VD: "Phòng Vocal - Đã có người")
    private String title;

    // Thời gian bắt đầu (Dạng String chuẩn ISO 8601: "2023-12-25T14:00:00")
    private String start;

    // Thời gian kết thúc
    private String end;

    // Màu sắc hiển thị (Vàng = Pending, Xanh = Completed)
    private String color;

    // Link bấm vào để xem chi tiết (nếu là Admin) hoặc xem phòng (nếu là Khách)
    private String url;
}