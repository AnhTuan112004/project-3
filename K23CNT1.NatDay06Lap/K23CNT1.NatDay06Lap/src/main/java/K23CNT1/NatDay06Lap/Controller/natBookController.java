package K23CNT1.NatDay06Lap.Controller;

import K23CNT1.NatDay06Lap.Service.natBookService;
import K23CNT1.NatDay06Lap.Service.natAuthorService;

import K23CNT1.NatDay06Lap.entity.natauthor;
import K23CNT1.NatDay06Lap.entity.natbook;
import K23CNT1.NatDay06Lap.entity.natBookAuthor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection; // Thêm import cho Collection

@Controller
@RequestMapping("/books")
public class natBookController {
    @Autowired
    private natBookService bookService;
    @Autowired
    private natAuthorService authorService;
    private static final String UPLOAD_DIR =
            "src/main/resources/static/";

    private static final String
            UPLOAD_PathFile="images/products/";

    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books/book-list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new natbook());
        model.addAttribute("authors", authorService.getAllAuthors());
        return "books/book-form";
    }

    // ✅ SỬA LỖI: Ánh xạ tới cả /new và /edit/{natid} để form hoạt động đúng cho cả hai trường hợp
    @PostMapping({"/new", "/edit/{natid}"})
    public String saveBook(@ModelAttribute natbook book
            , @RequestParam Long primaryAuthorId
            , @RequestParam(required = false) List<Long> allAuthorIds
            , @RequestParam("imageBook") MultipartFile imageFile) {

        // 1. Xử lý Image URL cũ (chỉ khi cập nhật)
        if (book.getNatid() != null) {
            natbook existingBook = bookService.getBookById(book.getNatid());
            // Giữ lại đường dẫn ảnh cũ nếu không có tệp mới được tải lên
            if (imageFile.isEmpty() && existingBook != null && existingBook.getNatimgUrl() != null) {
                book.setNatimgUrl(existingBook.getNatimgUrl());
            }
        }

        // 2. Xử lý File Upload (Lưu tệp mới)
        if(!imageFile.isEmpty()) {
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR+UPLOAD_PathFile);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String originalFilename = StringUtils.cleanPath(imageFile.getOriginalFilename());
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

                String newFileName = book.getNatcode() + fileExtension;
                Path filePath = uploadPath.resolve(newFileName);
                // Sử dụng REPLACE_EXISTING để ghi đè file cũ (nếu có)
                Files.copy(imageFile.getInputStream(), filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                book.setNatimgUrl("/" + UPLOAD_PathFile + newFileName);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 3. Xử lý Mối quan hệ N-to-M có thuộc tính (Chủ biên/Đồng tác giả)
        List<natBookAuthor> bookAuthors = new ArrayList<>();

        if (allAuthorIds != null && !allAuthorIds.isEmpty()) {
            // ✅ ĐẢM BẢO authorService có phương thức findAllById(Collection<Long> ids)
            // Cần phải đổi kiểu trả về sang Collection hoặc List
            Collection<natauthor> allAuthors = authorService.findAllById(allAuthorIds);

            for (natauthor author : allAuthors) {
                natBookAuthor ba = new natBookAuthor();
                ba.setBook(book);
                ba.setAuthor(author);

                // Gán vai trò (isPrimaryAuthor)
                if (author.getNatid().equals(primaryAuthorId)) {
                    ba.setIsPrimaryAuthor(true);
                } else {
                    ba.setIsPrimaryAuthor(false);
                }

                bookAuthors.add(ba);
            }
        }

        book.setBookAuthors(bookAuthors);

        // 4. Lưu sách (CascadeType.ALL sẽ tự động lưu/cập nhật natBookAuthor)
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{natid}")
    public String showEditForm(@PathVariable("natid") Long natid, Model model) {
        natbook book = bookService.getBookById(natid);

        if (book == null) {
            return "redirect:/books";
        }

        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.getAllAuthors());

        return "books/book-form";
    }

    @GetMapping("/delete/{natid}")
    public String deleteBook(@PathVariable("natid") Long natid) {
        bookService.deleteBook(natid);
        return "redirect:/books";
    }
}