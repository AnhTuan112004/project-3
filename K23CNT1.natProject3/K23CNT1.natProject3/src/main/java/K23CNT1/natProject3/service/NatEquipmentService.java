package K23CNT1.natProject3.service;


import K23CNT1.natProject3.entity.NatEquipment;
import K23CNT1.natProject3.repository.NatEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NatEquipmentService {

    @Autowired
    private NatEquipmentRepository equipmentRepo;

    /**
     * 1. Lấy toàn bộ danh sách thiết bị
     * Mục đích: Hiển thị list Checkbox bên trang Đặt lịch (Client) để khách chọn thuê thêm.
     */
    public List<NatEquipment> getAllEquipments() {
        return equipmentRepo.findAll();
    }

    /**
     * 2. Lấy danh sách có phân trang
     * Mục đích: Hiển thị bảng quản lý kho bên trang Admin (tránh load hết 1000 cái nếu kho nhiều).
     * @param page: Trang hiện tại (bắt đầu từ 1)
     * @param size: Số lượng item mỗi trang
     */
    public Page<NatEquipment> getEquipmentsForAdmin(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return equipmentRepo.findAll(pageable);
    }

    /**
     * 3. Lấy chi tiết thiết bị theo ID
     * Mục đích: Điền dữ liệu vào form khi Admin bấm nút "Sửa".
     */
    public NatEquipment getEquipmentById(Long id) {
        return equipmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thiết bị có ID: " + id));
    }

    /**
     * 4. Lưu thiết bị (Create & Update)
     * Mục đích: Dùng chung cho cả chức năng Thêm mới và Cập nhật.
     * Nếu object có ID -> Update. Nếu không có ID -> Create.
     */
    public void saveEquipment(NatEquipment equipment) {
        // Có thể thêm logic validate tại đây (VD: Giá không được âm)
        if (equipment.getNatprice().doubleValue() < 0) {
            throw new RuntimeException("Giá thuê thiết bị không được nhỏ hơn 0");
        }
        equipmentRepo.save(equipment);
    }

    /**
     * 5. Xóa thiết bị
     * Mục đích: Xóa khỏi kho.
     * Lưu ý: Nếu thiết bị đã từng được thuê (có trong bảng NatBookingEquipment),
     * việc xóa có thể gây lỗi khóa ngoại (Foreign Key Constraint).
     * Nên dùng try-catch ở Controller để báo lỗi "Không thể xóa vì đã có lịch sử thuê".
     */
    public void deleteEquipment(Long id) {
        if (equipmentRepo.existsById(id)) {
            equipmentRepo.deleteById(id);
        } else {
            throw new RuntimeException("Thiết bị không tồn tại để xóa");
        }
    }

    /**
     * 6. Tìm kiếm thiết bị (Optional)
     * Dùng cho Admin tìm nhanh thiết bị trong kho
     */
    // public List<NatEquipment> searchEquipment(String keyword) {
    //    return equipmentRepo.findByNatnameContaining(keyword);
    //    (Cần viết thêm hàm này trong Repository nếu muốn dùng)
    // }
}