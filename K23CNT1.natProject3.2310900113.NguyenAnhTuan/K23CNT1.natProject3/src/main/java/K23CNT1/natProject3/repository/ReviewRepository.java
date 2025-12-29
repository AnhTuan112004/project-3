package K23CNT1.natProject3.repository;


import K23CNT1.natProject3.entity.Post;
import K23CNT1.natProject3.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.booking.studio.id = :studioId")
    List<Review> findAllByStudioId(@Param("studioId") Long studioId);
}
