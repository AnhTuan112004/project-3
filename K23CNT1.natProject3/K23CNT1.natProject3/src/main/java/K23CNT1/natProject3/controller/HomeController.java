package K23CNT1.natProject3.controller;

import K23CNT1.natProject3.dto.BookingRequestDTO;
import K23CNT1.natProject3.entity.Post;
import K23CNT1.natProject3.entity.Studio;
import K23CNT1.natProject3.service.PostService;
import K23CNT1.natProject3.service.RecommendationService;
import K23CNT1.natProject3.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired private StudioService studioService;
    @Autowired private RecommendationService recommendationService;
    @Autowired private PostService postService;

    // ==========================================================
    // 1. TRANG CHỦ
    // ==========================================================
    @GetMapping(value = {"/", "/home"})
    public String home(Model model,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "page", defaultValue = "0") int page) {

        int pageSize = 6;
        Page<Studio> studioPage;

        if (StringUtils.hasText(keyword)) {
            studioPage = studioService.searchStudios(keyword, page, pageSize);
        } else {
            studioPage = studioService.getAllStudios(page, pageSize);
        }

        model.addAttribute("studioPage", studioPage);
        model.addAttribute("studios", studioPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studioPage.getTotalPages());
        model.addAttribute("keyword", keyword);

        // Blog: Lấy top 3 bài
        List<Post> allPosts = postService.getAllPosts();
        if (allPosts != null && !allPosts.isEmpty()) {
            List<Post> top3Posts = allPosts.stream().limit(3).collect(Collectors.toList());
            model.addAttribute("posts", top3Posts);
        }

        // [QUAN TRỌNG] Trả về "home" (không có dấu / ở đầu)
        // File phải nằm tại: src/main/resources/templates/home.html
        return "home";
    }

    // ==========================================================
    // 2. CHI TIẾT PHÒNG THU
    // ==========================================================
    @GetMapping("/studio/{id}")
    public String studioDetail(@PathVariable Long id, Model model) {
        Studio studio = studioService.getStudioById(id);
        if (studio == null) return "redirect:/home";

        model.addAttribute("studio", studio);

        if (recommendationService != null && studio.getStudioType() != null) {
            model.addAttribute("recommendedEquipments",
                    recommendationService.getRecommendedEquipments(studio.getStudioType()));
        }

        BookingRequestDTO bookingDTO = new BookingRequestDTO();
        bookingDTO.setStudioId(id);
        model.addAttribute("bookingRequest", bookingDTO);

        // [QUAN TRỌNG] Trả về "studio-detail"
        // File phải nằm tại: src/main/resources/templates/studio-detail.html
        return "studio-detail";
    }

    // ==========================================================
    // 3. DANH SÁCH BLOG
    // ==========================================================
    // URL truy cập là: http://localhost:8080/posts
    @GetMapping("/posts")
    public String blogList(Model model) {
        model.addAttribute("posts", postService.getAllPosts());

        // [QUAN TRỌNG] Trả về "blog-list"
        // File phải nằm tại: src/main/resources/templates/blog-list.html
        return "blog-list";
    }

    // ==========================================================
    // 4. CHI TIẾT BLOG
    // ==========================================================
    @GetMapping("/post/{id}")
    public String blogDetail(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);
        if (post == null) return "redirect:/posts";

        model.addAttribute("post", post);

        // [QUAN TRỌNG] Trả về "blog-detail"
        // File phải nằm tại: src/main/resources/templates/blog-detail.html
        return "blog-detail";
    }
}