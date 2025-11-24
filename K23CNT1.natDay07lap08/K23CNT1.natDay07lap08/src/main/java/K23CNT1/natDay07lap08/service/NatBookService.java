package K23CNT1.natDay07lap08.service;

import K23CNT1.natDay07lap08.dto.NatBookFormDto;
import K23CNT1.natDay07lap08.entity.NatAuthor;
import K23CNT1.natDay07lap08.entity.NatBook;

import java.util.List;

public interface NatBookService {
    List<NatAuthor> getAllAuthors();
    List<NatBook> getAllBooks();
    void saveBookWithAuthors(NatBookFormDto dto); // Create & Update
    NatBookFormDto getBookForEdit(Long id);       // Get Data for Update
    void deleteBook(Long id);                     // Delete
}