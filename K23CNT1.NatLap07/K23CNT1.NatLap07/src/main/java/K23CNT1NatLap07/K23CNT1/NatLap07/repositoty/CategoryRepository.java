package K23CNT1NatLap07.K23CNT1.NatLap07.repositoty;


import K23CNT1NatLap07.K23CNT1.NatLap07.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CategoryRepository extends
        JpaRepository<Category, Long> {
}
