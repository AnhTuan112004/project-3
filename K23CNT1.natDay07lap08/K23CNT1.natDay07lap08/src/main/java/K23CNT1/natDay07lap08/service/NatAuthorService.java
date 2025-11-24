package K23CNT1.natDay07lap08.service;


import K23CNT1.natDay07lap08.entity.NatAuthor;

import java.util.List;

public interface NatAuthorService {
    List<NatAuthor> getAllAuthors();
    NatAuthor getAuthorById(Long id);
    void saveAuthor(NatAuthor author);
    void deleteAuthor(Long id);
}