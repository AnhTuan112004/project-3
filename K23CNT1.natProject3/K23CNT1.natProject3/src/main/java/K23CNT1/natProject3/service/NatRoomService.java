package K23CNT1.natProject3.service;


import K23CNT1.natProject3.entity.NatRoom;
import K23CNT1.natProject3.repository.NatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NatRoomService {

    @Autowired private NatRoomRepository roomRepo;

    // Lấy danh sách có phân trang + tìm kiếm
    public Page<NatRoom> getAllRooms(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        if (keyword != null && !keyword.isEmpty()) {
            return roomRepo.searchRooms(keyword, pageable);
        }
        return roomRepo.findAll(pageable);
    }

    // Lấy chi tiết 1 phòng
    public NatRoom getRoomById(Long id) {
        return roomRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng có ID: " + id));
    }

    // Lưu phòng (Thêm mới hoặc Sửa)
    public void saveRoom(NatRoom room) {
        roomRepo.save(room);
    }

    // Xóa phòng
    public void deleteRoom(Long id) {
        roomRepo.deleteById(id);
    }
}