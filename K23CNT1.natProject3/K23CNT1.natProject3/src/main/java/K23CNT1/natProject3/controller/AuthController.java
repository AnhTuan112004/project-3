package K23CNT1.natProject3.controller;


import K23CNT1.natProject3.dto.UserRegistrationDTO;
import K23CNT1.natProject3.entity.NatUser;
import K23CNT1.natProject3.service.NatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private NatUserService userService;

    // Trang Đăng nhập (Spring Security tự xử lý POST, mình chỉ làm giao diện GET)
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login"; // Tạo file templates/auth/login.html
    }

    // Trang Đăng ký
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("userDto", new UserRegistrationDTO());
        return "auth/register";
    }

    // Xử lý Đăng ký
    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserRegistrationDTO userDto, Model model) {
        // Kiểm tra mật khẩu nhập lại
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            model.addAttribute("error", "Mật khẩu nhập lại không khớp!");
            return "auth/register";
        }

        try {
            NatUser newUser = new NatUser();
            newUser.setNatusername(userDto.getUsername());
            newUser.setNatpassword(userDto.getPassword()); // Service sẽ mã hóa sau
            newUser.setNatfullname(userDto.getFullname());

            userService.registerUser(newUser);

            return "redirect:/login?success"; // Chuyển sang login báo thành công
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage()); // VD: Trùng username
            return "auth/register";
        }
    }
}