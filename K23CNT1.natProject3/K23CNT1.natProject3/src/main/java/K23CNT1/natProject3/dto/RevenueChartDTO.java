package K23CNT1.natProject3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RevenueChartDTO {
    private String month;  // Tháng 1, Tháng 2...
    private Long revenue;  // Doanh thu
}