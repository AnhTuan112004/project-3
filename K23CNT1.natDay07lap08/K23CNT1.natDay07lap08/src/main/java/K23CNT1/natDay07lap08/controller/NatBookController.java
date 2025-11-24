package K23CNT1.natDay07lap08.controller;

import K23CNT1.natDay07lap08.dto.NatBookFormDto;
import K23CNT1.natDay07lap08.service.NatBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/nat-books")
public class NatBookController {
    @Autowired private NatBookService natBookService;

    // 1. List
    @GetMapping("")
    public String showListBooks(Model model) {
        model.addAttribute("natListBooks", natBookService.getAllBooks());
        return "nat-book-list";
    }

    // 2. Form Create
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("natBookForm", new NatBookFormDto());
        model.addAttribute("natListAuthors", natBookService.getAllAuthors());
        return "nat-book-form";
    }

    // 3. Form Edit
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        NatBookFormDto dto = natBookService.getBookForEdit(id);
        if (dto == null) return "redirect:/nat-books";

        model.addAttribute("natBookForm", dto);
        model.addAttribute("natListAuthors", natBookService.getAllAuthors());
        return "nat-book-form";
    }

    // 4. Delete
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        natBookService.deleteBook(id);
        return "redirect:/nat-books";
    }

    // 5. Save (Create + Update)
    @PostMapping("/save")
    public String saveBook(@ModelAttribute("natBookForm") NatBookFormDto natDto) {
        natBookService.saveBookWithAuthors(natDto);
        return "redirect:/nat-books";
    }
}