package K23CNT1.natDay07lap08.dto;

import K23CNT1.natDay07lap08.entity.NatBook;
import lombok.Data;
import java.util.List;

@Data
public class NatBookFormDto {

    // --- SỬA LẠI DÒNG NÀY ---
    // Phải khởi tạo = new NatBook() để tránh lỗi null trên form thêm mới
    private NatBook natBook = new NatBook();
    // ------------------------

    private List<Long> natAuthorIds;
    private Long natMainEditorId;
}