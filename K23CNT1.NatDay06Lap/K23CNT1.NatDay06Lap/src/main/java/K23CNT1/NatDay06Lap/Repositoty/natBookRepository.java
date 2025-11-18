package K23CNT1.NatDay06Lap.Repositoty;

import K23CNT1.NatDay06Lap.entity.natbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface natBookRepository extends JpaRepository<natbook, Long> {
}