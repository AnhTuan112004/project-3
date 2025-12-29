package K23CNT1.natProject3.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingRequestDTO {

    private Long userId;   // ID người đặt
    private Long studioId; // ID phòng muốn đặt

    // @DateTimeFormat giúp Spring tự convert chuỗi từ HTML input type="datetime-local" sang LocalDateTime
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endTime;

    // Danh sách thiết bị thuê thêm (nếu có)
    private List<EquipmentRequest> equipments;

    // Class con để hứng ID và Số lượng thiết bị
    @Data
    public static class EquipmentRequest {
        private Long equipmentId;
        private int quantity;
    }
}