package K23CNT1.NatDay06Lap.Repositoty;

import K23CNT1.NatDay06Lap.entity.natBookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository cho Entity liên kết natBookAuthor.
 * * Kế thừa JpaRepository với:
 * - Kiểu Entity là natBookAuthor
 * - Kiểu dữ liệu của Khóa chính (ID) là Long
 */
@Repository
public interface natBookAuthorRepository extends
        JpaRepository<natBookAuthor, Long> {

    // Spring Data JPA sẽ tự động cung cấp các phương thức CRUD cơ bản
    // như save(), findById(), findAll(), delete(), v.v.

    // Nếu cần các truy vấn tùy chỉnh, bạn có thể thêm các phương thức
    // tại đây (ví dụ: findByBook_Natid(Long bookId))
}