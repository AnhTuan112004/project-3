package K23CNT1.natProject3.repository;


import K23CNT1.natProject3.entity.NatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NatRoomRepository extends JpaRepository<NatRoom, Long> {

    // Tìm kiếm phòng theo Tên hoặc Loại phòng (Dùng cho thanh search ở trang chủ/admin)
    @Query("SELECT r FROM NatRoom r WHERE r.natname LIKE %?1% OR r.natRoomType.natname LIKE %?1%")
    Page<NatRoom> searchRooms(String keyword, Pageable pageable);

    // Lấy danh sách phòng theo trạng thái (VD: Chỉ lấy phòng đang ACTIVE cho khách xem)
    Page<NatRoom> findByNatstatus(String status, Pageable pageable);
}