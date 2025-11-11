package k23cnt1.natlesson05.k23cnt1.natlesson05.controller;

import k23cnt1.natlesson05.k23cnt1.natlesson05.entity.Info;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class HomeController {

    @GetMapping
    public String index(Model model) {
        model.addAttribute("title", "Trang chủ - Devmaster");
        return "index";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("title", "Profile - Devmaster");

        List<Info> profile = new ArrayList<>();
        profile.add(new Info("Anh Tuấn",
                "Tuấn",
                "nguyenanhtuant647@gmail.com",
                "https://devmaster.edu.vn"));

        model.addAttribute("natPrfile", profile);
        return "profile";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "Giới thiệu");
        return "about";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("title", "Liên hệ");
        return "contact";
    }
}