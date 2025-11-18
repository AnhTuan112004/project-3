package K23CNT1.NatDay06Lap.Service;

import K23CNT1.NatDay06Lap.Repositoty.natAuthorRepository;
import K23CNT1.NatDay06Lap.entity.natauthor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class natAuthorService {

    @Autowired
    private natAuthorRepository authorRepository;

    public List<natauthor> getAllAuthors() {
        return authorRepository.findAll();
    }

    public natauthor saveAuthor(natauthor author) {
        return authorRepository.save(author);
    }

    public natauthor getAuthorById(Long natid) {
        return authorRepository.findById(natid).orElse(null);
    }

    public void deleteAuthor(Long natid) {
        authorRepository.deleteById(natid);
    }

    public List<natauthor> findAllById(List<Long> ids) {
        // findAllById là một phương thức tích hợp sẵn trong JpaRepository
        return authorRepository.findAllById(ids);
    }
} // ⬅️ Đã bổ sung dấu đóng ngoặc nhọn