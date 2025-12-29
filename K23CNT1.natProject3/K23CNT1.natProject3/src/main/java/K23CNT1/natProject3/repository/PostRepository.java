package K23CNT1.natProject3.repository;

import K23CNT1.natProject3.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // ================== 1. CÁC HÀM CŨ (TRẢ VỀ LIST) ==================
    // Dùng cho các phần hiển thị không cần phân trang (ví dụ: Top 3 bài mới nhất ở trang chủ)

    // Lấy tất cả bài viết sắp xếp theo ngày tạo mới nhất
    List<Post> findAllByOrderByCreatedAtDesc();

    // Tìm kiếm bài viết theo tiêu đề
    List<Post> findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(String title);


    // ================== 2. CÁC HÀM MỚI (HỖ TRỢ PHÂN TRANG) ==================
    // Dùng cho trang Quản lý Admin (AdminController)

    /**
     * Tìm kiếm bài viết theo tiêu đề có phân trang.
     * Lưu ý: Không cần thêm "OrderByCreatedAtDesc" vào tên hàm,
     * vì đối tượng 'Pageable' đã chứa thông tin sắp xếp (Sort) nếu chúng ta truyền vào từ Service.
     */
    Page<Post> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    // Lưu ý: Hàm findAll(Pageable pageable) đã có sẵn trong JpaRepository
    // nên không cần khai báo lại ở đây.
}