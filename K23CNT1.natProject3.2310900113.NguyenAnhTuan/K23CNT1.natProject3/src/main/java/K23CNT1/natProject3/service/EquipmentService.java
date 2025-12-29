package K23CNT1.natProject3.service;

import K23CNT1.natProject3.entity.Equipment;
import K23CNT1.natProject3.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepo;

    // 1. Lấy tất cả thiết bị (Dạng List - Dùng cho Dropdown hoặc list nhỏ)
    public List<Equipment> getAllEquipments() {
        return equipmentRepo.findAll();
    }

    // [MỚI] Lấy tất cả có phân trang (Dùng cho bảng Admin)
    public Page<Equipment> getAllEquipments(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return equipmentRepo.findAll(pageable);
    }

    // 2. Lấy thiết bị theo ID (để sửa)
    public Equipment getEquipmentById(Long id) {
        return equipmentRepo.findById(id).orElse(null);
    }

    // 3. Lưu thiết bị (Thêm mới hoặc Cập nhật)
    @Transactional
    public void saveEquipment(Equipment equipment) {
        equipmentRepo.save(equipment);
    }

    // 4. Xóa thiết bị
    @Transactional
    public void deleteEquipment(Long id) {
        equipmentRepo.deleteById(id);
    }

    // 5. Tìm kiếm thiết bị theo tên (Hỗ trợ Admin tìm nhanh)
    public List<Equipment> searchEquipments(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return equipmentRepo.findByNameContainingIgnoreCase(keyword);
        }
        return equipmentRepo.findAll();
    }

    // [MỚI] Tìm kiếm có phân trang
    public Page<Equipment> searchEquipments(String keyword, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        if (keyword != null && !keyword.isEmpty()) {
            return equipmentRepo.findByNameContainingIgnoreCase(keyword, pageable);
        }
        return equipmentRepo.findAll(pageable);
    }
}