package K23CNT1.natProject3.repository;


import K23CNT1.natProject3.entity.NatRoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NatRoomTypeRepository extends JpaRepository<NatRoomType, Long> {
    // Tìm theo tên (để tránh trùng lặp khi thêm mới)
    NatRoomType findByNatname(String natname);
}