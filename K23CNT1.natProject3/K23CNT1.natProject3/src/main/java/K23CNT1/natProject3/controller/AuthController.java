package K23CNT1.natProject3.controller;


import K23CNT1.natProject3.dto.UserRegisterDTO;
import K23CNT1.natProject3.entity.User;
import K23CNT1.natProject3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired private UserService userService;

    // Trang Login
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Trả về file login.html
    }

    // Trang Đăng ký
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new UserRegisterDTO());
        return "register"; // Trả về file register.html
    }

    // Xử lý khi bấm nút Đăng ký
    @PostMapping("/register")
    public String registerProcess(@ModelAttribute("user") UserRegisterDTO dto) {
        // Map DTO sang Entity (Bạn có thể dùng MapStruct để gọn hơn)
        User newUser = new User();
        newUser.setUsername(dto.getUsername());
        newUser.setPassword(dto.getPassword());
        newUser.setFullName(dto.getFullName());
        newUser.setEmail(dto.getEmail());
        newUser.setPhone(dto.getPhone());

        userService.registerUser(newUser);

        return "redirect:/login?success"; // Đăng ký xong chuyển về login
    }
}