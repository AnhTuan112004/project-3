package K23CNT1.natDay07lap08.service;


import K23CNT1.natDay07lap08.entity.NatAuthor;
import K23CNT1.natDay07lap08.repository.NatAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NatAuthorServiceImpl implements NatAuthorService {

    @Autowired
    private NatAuthorRepository natAuthorRepo;

    @Override
    public List<NatAuthor> getAllAuthors() {
        return natAuthorRepo.findAll();
    }

    @Override
    public NatAuthor getAuthorById(Long id) {
        return natAuthorRepo.findById(id).orElse(null);
    }

    @Override
    public void saveAuthor(NatAuthor author) {
        natAuthorRepo.save(author);
    }

    @Override
    public void deleteAuthor(Long id) {
        // Lưu ý: Nếu tác giả này đang có sách, việc xóa có thể gây lỗi Constraint DB
        // Tùy vào yêu cầu mà bạn xử lý (xóa sách trước hoặc chặn xóa)
        // Ở đây mình xóa cơ bản:
        natAuthorRepo.deleteById(id);
    }
}