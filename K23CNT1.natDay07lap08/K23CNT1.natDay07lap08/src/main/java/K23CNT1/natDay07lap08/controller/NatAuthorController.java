package K23CNT1.natDay07lap08.controller;


import K23CNT1.natDay07lap08.entity.NatAuthor;
import K23CNT1.natDay07lap08.service.NatAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/nat-authors")
public class NatAuthorController {

    @Autowired
    private NatAuthorService natAuthorService;

    // 1. Danh sách Tác giả
    @GetMapping("")
    public String showList(Model model) {
        model.addAttribute("natListAuthors", natAuthorService.getAllAuthors());
        return "nat-author-list";
    }

    // 2. Form Thêm mới
    @GetMapping("/create")
    public String createAuth(Model model) {
        model.addAttribute("natAuthor", new NatAuthor());
        return "nat-author-form";
    }

    // 3. Form Sửa
    @GetMapping("/edit/{id}")
    public String editAuth(@PathVariable("id") Long id, Model model) {
        NatAuthor auth = natAuthorService.getAuthorById(id);
        if (auth == null) {
            return "redirect:/nat-authors";
        }
        model.addAttribute("natAuthor", auth);
        return "nat-author-form";
    }

    // 4. Lưu (Create + Update)
    @PostMapping("/save")
    public String saveAuth(@ModelAttribute("natAuthor") NatAuthor natAuthor) {
        natAuthorService.saveAuthor(natAuthor);
        return "redirect:/nat-authors";
    }

    // 5. Xóa
    @GetMapping("/delete/{id}")
    public String deleteAuth(@PathVariable("id") Long id) {
        try {
            natAuthorService.deleteAuthor(id);
        } catch (Exception e) {
            // Xử lý nếu lỗi ràng buộc khóa ngoại (tác giả đang có sách)
            System.out.println("Không thể xóa tác giả này vì đang có sách tham gia!");
        }
        return "redirect:/nat-authors";
    }
}