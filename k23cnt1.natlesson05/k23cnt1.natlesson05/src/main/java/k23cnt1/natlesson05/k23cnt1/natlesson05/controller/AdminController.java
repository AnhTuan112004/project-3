package k23cnt1.natlesson05.k23cnt1.natlesson05.controller;

import k23cnt1.natlesson05.k23cnt1.natlesson05.entity.SimpleUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // == Database tạm (In-memory) ==
    private List<SimpleUser> userList = new ArrayList<>();
    private int idCounter = 0; // Biến đếm để tạo ID

    // Dùng constructor để tạo dữ liệu mẫu 1 lần duy nhất
    public AdminController() {
        userList.add(new SimpleUser(++idCounter, "anhtuan", "tuan@gmail.com", true));
        userList.add(new SimpleUser(++idCounter, "devmaster", "admin@devmaster.edu.vn", true));
        userList.add(new SimpleUser(++idCounter, "k23cnt1", "k23@gmail.com", false));
    }

    /**
     * SỬA LỖI 404:
     * Tự động chuyển hướng từ /admin sang /admin/users
     */
    @GetMapping
    public String redirectToUserList() {
        return "redirect:/admin/users";
    }

    /**
     * READ: Hiển thị danh sách user
     */
    @GetMapping("/users")
    public String showUserList(Model model) {
        model.addAttribute("title", "Danh sách User");
        model.addAttribute("users", userList);
        return "admin/user-list";
    }

    /**
     * CREATE (Step 1): Hiển thị form thêm mới
     */
    @GetMapping("/users/add")
    public String showForm(Model model) {
        model.addAttribute("title", "Thêm User mới");
        model.addAttribute("user", new SimpleUser());
        return "admin/user-form";
    }

    /**
     * UPDATE (Step 1): Hiển thị form sửa
     */
    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Optional<SimpleUser> userToEdit = userList.stream()
                .filter(user -> user.getId() == id)
                .findFirst();

        if (userToEdit.isPresent()) {
            model.addAttribute("title", "Sửa User");
            model.addAttribute("user", userToEdit.get());
            return "admin/user-form";
        }

        return "redirect:/admin/users";
    }


    /**
     * CREATE (Step 2) & UPDATE (Step 2): Lưu thông tin
     */
    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute("user") SimpleUser user) {

        if (user.getId() == 0) {
            // == TẠO MỚI (ID = 0) ==
            user.setId(++idCounter);
            userList.add(user);
        } else {
            // == CẬP NHẬT (ID != 0) ==
            Optional<SimpleUser> userToUpdate = userList.stream()
                    .filter(u -> u.getId() == user.getId())
                    .findFirst();
            if (userToUpdate.isPresent()) {
                SimpleUser existingUser = userToUpdate.get();
                existingUser.setUsername(user.getUsername());
                existingUser.setEmail(user.getEmail());
                existingUser.setStatus(user.isStatus());
            }
        }
        return "redirect:/admin/users";
    }

    /**
     * DELETE: Xóa user
     */
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userList.removeIf(user -> user.getId() == id);
        return "redirect:/admin/users";
    }
}