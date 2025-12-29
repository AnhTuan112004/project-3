package K23CNT1.natProject3.repository;

import K23CNT1.natProject3.entity.StudioDemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudioDemoRepository extends JpaRepository<StudioDemo, Long> {
    // Tìm danh sách demo dựa theo ID của phòng thu cha
    List<StudioDemo> findByStudioId(Long studioId);
}