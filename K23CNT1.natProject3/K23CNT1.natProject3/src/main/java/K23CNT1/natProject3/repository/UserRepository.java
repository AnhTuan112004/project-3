package K23CNT1.natProject3.repository;

import K23CNT1.natProject3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    // Tìm kỹ thuật viên dựa trên Role (quan hệ join nat_roles)
    // Giả sử tên Role là 'ROLE_STAFF'
    // List<User> findByRoleName(String roleName);
}