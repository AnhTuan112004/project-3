package K23CNT1.natProject3.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class StudioSearchDTO {
    private String keyword;    // Tìm theo tên
    private String studioType; // Tìm theo loại (VOCAL/LIVE)
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}