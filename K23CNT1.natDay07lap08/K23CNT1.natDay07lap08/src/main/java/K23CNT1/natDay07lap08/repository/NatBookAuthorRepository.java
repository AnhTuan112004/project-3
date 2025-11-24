package K23CNT1.natDay07lap08.repository;


import K23CNT1.natDay07lap08.entity.NatBook;
import K23CNT1.natDay07lap08.entity.NatBookAuthor;
import K23CNT1.natDay07lap08.entity.NatBookAuthorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NatBookAuthorRepository extends JpaRepository<NatBookAuthor, NatBookAuthorId> {
    // Hàm xóa tất cả quan hệ theo sách (để dùng khi Update)
    @Transactional
    void deleteByNatBook(NatBook natBook);
}