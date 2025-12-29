package K23CNT1.natProject3.repository;

import K23CNT1.natProject3.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Tìm theo nat_role_name
    Optional<Role> findByName(String name);
}