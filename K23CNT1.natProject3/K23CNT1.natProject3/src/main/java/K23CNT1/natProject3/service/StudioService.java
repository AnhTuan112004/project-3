package K23CNT1.natProject3.service;

import K23CNT1.natProject3.entity.Studio;
import K23CNT1.natProject3.entity.StudioDemo; // [MỚI]
import K23CNT1.natProject3.repository.StudioDemoRepository; // [MỚI]
import K23CNT1.natProject3.repository.StudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudioService {

    @Autowired
    private StudioRepository studioRepo;

    @Autowired // [MỚI] Inject thêm Repository của Demo để xóa nhạc
    private StudioDemoRepository demoRepo;

    // ================== 1. PHƯƠNG THỨC HỖ TRỢ PHÂN TRANG (HOME PAGE) ==================

    /**
     * Lấy danh sách phòng đang ACTIVE có phân trang
     */
    public Page<Studio> getActiveStudios(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return studioRepo.findByStatus("ACTIVE", pageable);
    }

    /**
     * Tìm kiếm phòng theo tên có phân trang
     */
    public Page<Studio> searchStudios(String keyword, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        if (keyword == null || keyword.trim().isEmpty()) {
            return getActiveStudios(pageNo, pageSize);
        }
        return studioRepo.findByNameContainingIgnoreCase(keyword, pageable);
    }

    // ================== 2. CÁC PHƯƠNG THỨC CŨ (GIỮ NGUYÊN) ==================

    public List<Studio> getAllStudios() {
        return studioRepo.findAll();
    }

    public List<Studio> getActiveStudios() {
        return studioRepo.findByStatus("ACTIVE");
    }

    public Studio getStudioById(Long id) {
        return studioRepo.findById(id).orElse(null);
    }

    public List<Studio> searchStudios(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getActiveStudios();
        }
        return studioRepo.findByNameContainingIgnoreCase(keyword);
    }

    public List<Studio> getStudiosByType(String type) {
        return studioRepo.findByStudioTypeAndStatus(type, "ACTIVE");
    }

    // ================== NGHIỆP VỤ ADMIN (CẬP NHẬT/XÓA) ==================

    @Transactional
    public void saveStudio(Studio studio) {
        if (studio.getId() == null && (studio.getStatus() == null || studio.getStatus().isEmpty())) {
            studio.setStatus("ACTIVE");
        }
        studioRepo.save(studio);
    }

    @Transactional
    public void deleteStudio(Long id) {
        studioRepo.deleteById(id);
    }

    /**
     * [MỚI - QUAN TRỌNG] Xóa bản thu mẫu và trả về ID của Studio để redirect
     * Hàm này giúp sửa lỗi 404 khi xóa file nhạc
     */
    @Transactional
    public Long deleteStudioDemo(Long demoId) {
        // 1. Tìm bản demo
        StudioDemo demo = demoRepo.findById(demoId).orElse(null);

        if (demo != null) {
            // 2. Lấy ID phòng thu cha (để tí nữa quay lại trang sửa phòng thu đó)
            Long studioId = demo.getStudio().getId();

            // 3. Xóa bản demo
            demoRepo.delete(demo);

            // 4. Trả về ID phòng thu
            return studioId;
        }
        return null;
    }
    // Thêm hàm lấy tất cả phòng có phân trang (cho Admin)
    public Page<Studio> getAllStudios(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return studioRepo.findAll(pageable);
    }
}