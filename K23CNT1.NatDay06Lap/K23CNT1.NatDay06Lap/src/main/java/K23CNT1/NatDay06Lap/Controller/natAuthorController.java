package K23CNT1.NatDay06Lap.Controller;

import K23CNT1.NatDay06Lap.Service.natAuthorService;
import K23CNT1.NatDay06Lap.entity.natauthor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/authors") // Định tuyến cơ sở cho tất cả các yêu cầu liên quan đến tác giả
public class natAuthorController {

    @Autowired
    private natAuthorService authorService;

    // --- 1. Hiển thị danh sách Tác giả ---
    @GetMapping
    public String listAuthors(Model model) {
        List<natauthor> authors = authorService.getAllAuthors();
        model.addAttribute("authors", authors);
        return "authors/author-list"; // Trả về view: author-list.html
    }

    // --- 2. Hiển thị form Thêm mới/Chỉnh sửa Tác giả ---
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("author", new natauthor());
        return "authors/author-form"; // Trả về view: author-form.html
    }

    // --- 3. Xử lý lưu (Thêm mới/Chỉnh sửa) Tác giả ---
    @PostMapping("/save")
    public String saveAuthor(@ModelAttribute natauthor author) {
        authorService.saveAuthor(author);
        return "redirect:/authors"; // Chuyển hướng về trang danh sách
    }

    // --- 4. Hiển thị form Chỉnh sửa Tác giả theo ID ---
    @GetMapping("/edit/{natid}")
    public String showEditForm(@PathVariable("natid") Long natid, Model model) {
        natauthor author = authorService.getAuthorById(natid);
        if (author == null) {
            // Xử lý khi không tìm thấy tác giả
            return "redirect:/authors";
        }
        model.addAttribute("author", author);
        return "authors/author-form"; // Dùng lại form thêm/sửa
    }

    // --- 5. Xóa Tác giả theo ID ---
    @GetMapping("/delete/{natid}")
    public String deleteAuthor(@PathVariable("natid") Long natid) {
        authorService.deleteAuthor(natid);
        return "redirect:/authors"; // Chuyển hướng về trang danh sách
    }
}