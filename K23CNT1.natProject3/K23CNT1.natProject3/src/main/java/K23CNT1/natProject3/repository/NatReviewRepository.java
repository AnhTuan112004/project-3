package K23CNT1.natProject3.repository;


import K23CNT1.natProject3.entity.NatReview;
import K23CNT1.natProject3.entity.NatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NatReviewRepository extends JpaRepository<NatReview, Long> {

    /**
     * 1. Lấy danh sách đánh giá của một phòng cụ thể.
     * Sắp xếp theo thời gian: Mới nhất lên đầu (Desc).
     * Dùng cho trang: Client - Room Detail.
     */
    List<NatReview> findByNatRoomOrderByNatcreatedAtDesc(NatRoom natRoom);

    /**
     * 2. Tìm đánh giá dựa trên ID đơn hàng.
     * Mục đích: Kiểm tra xem đơn hàng này khách đã viết review chưa.
     * Nếu trả về null -> Chưa review -> Hiện nút "Đánh giá".
     * Nếu có dữ liệu -> Đã review -> Hiện nút "Xem lại đánh giá".
     */
    NatReview findByNatBooking_Natid(Long bookingId);

    /**
     * 3. Tính điểm trung bình (Average Rating) của một phòng.
     * @param roomId: ID của phòng cần tính.
     * @return Double: Điểm trung bình (VD: 4.5), có thể null nếu chưa có ai đánh giá.
     */
    @Query("SELECT AVG(r.natrating) FROM NatReview r WHERE r.natRoom.natid = ?1")
    Double getAverageRating(Long roomId);

    /**
     * 4. (Tùy chọn) Lấy danh sách review của một User cụ thể.
     * Dùng cho trang: Lịch sử đánh giá của tôi.
     */
    List<NatReview> findByNatUser_NatidOrderByNatcreatedAtDesc(Long userId);

    Object findByNatRoom(NatRoom room);
}