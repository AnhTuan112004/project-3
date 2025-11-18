package K23CNT1.NatDay06Lap.Service;

import K23CNT1.NatDay06Lap.Repositoty.natBookRepository;
import K23CNT1.NatDay06Lap.entity.natbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import bắt buộc
import java.util.List;
import java.util.Optional;

@Service
public class natBookService {

    @Autowired
    private natBookRepository bookRepository;

    /**
     * Lấy danh sách tất cả sách.
     * ✅ THÊM @Transactional để giữ Session mở.
     */
    @Transactional
    public List<natbook> getAllBooks() {
        // Vì bookAuthors là LAZY, @Transactional sẽ đảm bảo truy vấn được thực hiện
        // khi View cố gắng truy cập bookAuthors sau đó.
        return bookRepository.findAll();
    }

    /**
     * Lưu hoặc cập nhật một cuốn sách.
     * @Transactional đã có sẵn, đảm bảo tính nguyên tử cho quá trình lưu/cập nhật Cascade.
     */
    @Transactional
    public natbook saveBook(natbook book) {
        return bookRepository.save(book);
    }

    /**
     * Lấy sách theo ID (natid).
     * ✅ THÊM @Transactional để đảm bảo bookAuthors được tải khi Controller/View truy cập
     * (thường xảy ra trong GET /books/edit/{id}).
     */
    @Transactional
    public natbook getBookById(Long natid) {
        return bookRepository.findById(natid).orElse(null);
    }

    /**
     * Xóa sách theo ID (natid).
     * @Transactional giúp việc xóa cascade diễn ra an toàn.
     */
    @Transactional
    public void deleteBook(Long natid) {
        bookRepository.deleteById(natid);
    }
}