package K23CNT1.natProject3.service;

import K23CNT1.natProject3.entity.Equipment;
import K23CNT1.natProject3.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RecommendationService {

    @Autowired
    private EquipmentRepository equipmentRepo;

    /**
     * Gợi ý thiết bị dựa trên loại phòng thu (Studio Type)
     * Sử dụng các trường đã ánh xạ nat_category_tag trong Repository
     */
    public List<Equipment> getRecommendedEquipments(String studioType) {
        if (studioType == null || studioType.isEmpty()) {
            return Collections.emptyList();
        }

        String tagToSearch;

        // Map loại phòng (nat_type) sang Tag thiết bị (nat_category_tag)
        switch (studioType.toUpperCase()) {
            case "VOCAL":
                tagToSearch = "VOCAL"; // Gợi ý Micro cao cấp, Pop filter
                break;
            case "BAND":
                tagToSearch = "INSTRUMENT"; // Gợi ý Trống, Amply, Guitar
                break;
            case "MIXING":
                tagToSearch = "MONITOR"; // Gợi ý Loa kiểm âm, Controller
                break;
            default:
                // Nếu là loại phòng khác (ví dụ LIVE_ROOM cũ), tìm theo tag chung hoặc trả về trống
                tagToSearch = "GENERAL";
                break;
        }

        // Gọi EquipmentRepository đã cập nhật để tìm theo nat_category_tag
        List<Equipment> recommended = equipmentRepo.findByCategoryTag(tagToSearch);

        // Nếu không tìm thấy thiết bị theo tag riêng biệt, có thể trả về danh sách trống hoặc mặc định
        return recommended != null ? recommended : Collections.emptyList();
    }
}