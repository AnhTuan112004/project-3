package K23CNT1.natDay07lap08.service;

import K23CNT1.natDay07lap08.dto.NatBookFormDto;
import K23CNT1.natDay07lap08.entity.NatAuthor;
import K23CNT1.natDay07lap08.entity.NatBook;
import K23CNT1.natDay07lap08.entity.NatBookAuthor;
import K23CNT1.natDay07lap08.repository.NatAuthorRepository;
import K23CNT1.natDay07lap08.repository.NatBookAuthorRepository;
import K23CNT1.natDay07lap08.repository.NatBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class NatBookServiceImpl implements NatBookService {
    @Autowired private NatBookRepository natBookRepo;
    @Autowired private NatAuthorRepository natAuthorRepo;
    @Autowired private NatBookAuthorRepository natBookAuthorRepo;

    @Override public List<NatAuthor> getAllAuthors() { return natAuthorRepo.findAll(); }
    @Override public List<NatBook> getAllBooks() { return natBookRepo.findAll(); }

    @Override @Transactional
    public void deleteBook(Long id) { natBookRepo.deleteById(id); }

    @Override @Transactional
    public void saveBookWithAuthors(NatBookFormDto dto) {
        // 1. Lưu Sách
        NatBook savedBook = natBookRepo.save(dto.getNatBook());

        // 2. Xóa hết quan hệ cũ (để tránh lỗi trùng hoặc không xóa được khi bỏ tick)
        natBookAuthorRepo.deleteByNatBook(savedBook);

        // 3. Thêm quan hệ mới
        if (dto.getNatAuthorIds() != null) {
            for (Long authId : dto.getNatAuthorIds()) {
                NatAuthor author = natAuthorRepo.findById(authId).orElse(null);
                if (author != null) {
                    NatBookAuthor relation = new NatBookAuthor();
                    relation.setNatBook(savedBook);
                    relation.setNatAuthor(author);
                    // Check chủ biên
                    if (authId.equals(dto.getNatMainEditorId())) {
                        relation.setNatIsEditor(true);
                    } else {
                        relation.setNatIsEditor(false);
                    }
                    natBookAuthorRepo.save(relation);
                }
            }
        }
    }

    @Override
    public NatBookFormDto getBookForEdit(Long id) {
        NatBook book = natBookRepo.findById(id).orElse(null);
        if (book == null) return null;

        NatBookFormDto dto = new NatBookFormDto();
        dto.setNatBook(book);

        // Map ngược từ Entity sang DTO để hiển thị lên form
        List<Long> authorIds = new ArrayList<>();
        Long mainEditorId = null;

        for (NatBookAuthor relation : book.getNatBookAuthors()) {
            authorIds.add(relation.getNatAuthor().getNatAuthorId());
            if (Boolean.TRUE.equals(relation.getNatIsEditor())) {
                mainEditorId = relation.getNatAuthor().getNatAuthorId();
            }
        }
        dto.setNatAuthorIds(authorIds);
        dto.setNatMainEditorId(mainEditorId);
        return dto;
    }
}