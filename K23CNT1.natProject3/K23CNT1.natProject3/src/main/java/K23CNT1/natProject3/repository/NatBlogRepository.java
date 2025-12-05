package K23CNT1.natProject3.repository;


import K23CNT1.natProject3.entity.NatBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NatBlogRepository extends JpaRepository<NatBlog, Long> {
    // Lấy 5 bài viết mới nhất để hiện lên trang chủ
    List<NatBlog> findTop5ByOrderByNatcreatedAtDesc();
}