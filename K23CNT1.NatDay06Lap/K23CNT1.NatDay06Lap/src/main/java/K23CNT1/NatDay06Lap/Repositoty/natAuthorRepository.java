package K23CNT1.NatDay06Lap.Repositoty;

import K23CNT1.NatDay06Lap.entity.natauthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface natAuthorRepository extends
        JpaRepository<natauthor, Long> {
}