package K23CNT1.natDay07lap08.repository;

import K23CNT1.natDay07lap08.entity.NatAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NatAuthorRepository extends JpaRepository<NatAuthor, Long> { }