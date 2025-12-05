package K23CNT1.natProject3.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {

    // ID phòng khách muốn đặt
    private Long roomId;

    // Thời gian bắt đầu (Mapping với input type="datetime-local" bên HTML)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime;

    // Thời gian kết thúc
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endTime;

    // Danh sách ID các thiết bị khách tích chọn (Checkbox)
    // Ví dụ: Khách chọn Mic (ID=1) và Guitar (ID=3) -> List = [1, 3]
    private List<Long> selectedEquipmentIds;
}