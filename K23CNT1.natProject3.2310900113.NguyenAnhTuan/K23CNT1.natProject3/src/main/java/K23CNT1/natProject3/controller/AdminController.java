package K23CNT1.natProject3.controller;

import K23CNT1.natProject3.entity.*;
import K23CNT1.natProject3.repository.BookingRepository;
import K23CNT1.natProject3.repository.StudioDemoRepository;
import K23CNT1.natProject3.repository.StudioRepository;
import K23CNT1.natProject3.service.*; // Import các Service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // --- KHAI BÁO SERVICE ---
    @Autowired private StudioService studioService;
    @Autowired private BookingService bookingService;
    @Autowired private PostService postService;
    @Autowired private EquipmentService equipmentService;
    @Autowired private ChatService chatService; // [MỚI] Thêm Service Chat

    // --- KHAI BÁO REPOSITORY (Cho Dashboard) ---
    @Autowired private StudioRepository studioRepo;
    @Autowired private BookingRepository bookingRepo;
    @Autowired private StudioDemoRepository studioDemoRepo;

    // Đường dẫn lưu file
    private final Path rootLocation = Paths.get("uploads");

    // ========================================================================
    // 1. DASHBOARD
    // ========================================================================
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        long pendingCount = bookingRepo.countByStatus("PENDING");
        long studioCount = studioRepo.count();
        LocalDate now = LocalDate.now();
        BigDecimal monthlyRevenue = bookingRepo.sumRevenueByMonth(now.getMonthValue(), now.getYear());
        if (monthlyRevenue == null) monthlyRevenue = BigDecimal.ZERO;

        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("studioCount", studioCount);
        model.addAttribute("monthlyRevenue", monthlyRevenue);

        // [MỚI] Biến để highlight Sidebar
        model.addAttribute("activePage", "dashboard");

        return "admin/dashboard";
    }

    // ========================================================================
    // [MỚI] 2. QUẢN LÝ CHAT (HỖ TRỢ KHÁCH HÀNG)
    // ========================================================================
    @GetMapping("/chat")
    public String chatManager(Model model) {
        // Lấy danh sách người đã chat để hiển thị bên trái
        model.addAttribute("recentUsers", chatService.getRecentChatUsers());

        model.addAttribute("activePage", "chat"); // Highlight menu Chat
        return "admin/chat-dashboard";
    }

    // ========================================================================
    // 3. QUẢN LÝ PHÒNG THU
    // ========================================================================
    @GetMapping("/studios")
    public String studioManager(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 8;
        Page<Studio> pageResult = studioService.getAllStudios(page, pageSize);
        model.addAttribute("studios", pageResult.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageResult.getTotalPages());

        model.addAttribute("activePage", "studios"); // Highlight menu Studios
        return "admin/studio-manager";
    }

    @GetMapping("/studio/new")
    public String showCreateStudioForm(Model model) {
        model.addAttribute("studio", new Studio());
        model.addAttribute("pageTitle", "Thêm Phòng Thu Mới");
        model.addAttribute("activePage", "studios");
        return "admin/studio-form";
    }

    @GetMapping("/studio/edit/{id}")
    public String showEditStudioForm(@PathVariable Long id, Model model) {
        Studio studio = studioService.getStudioById(id);
        if (studio == null) return "redirect:/admin/studios";
        model.addAttribute("studio", studio);
        model.addAttribute("pageTitle", "Sửa Phòng: " + studio.getName());
        model.addAttribute("activePage", "studios");
        return "admin/studio-form";
    }

    @PostMapping("/studio/save")
    public String saveStudio(@ModelAttribute("studio") Studio studio,
                             @RequestParam("imageFile") MultipartFile imageFile,
                             @RequestParam("audioFile") MultipartFile audioFile,
                             RedirectAttributes ra) {
        try {
            if (!imageFile.isEmpty()) {
                String fileName = saveFile(imageFile, "img_");
                studio.setImageUrl("/uploads/" + fileName);
            } else if (studio.getId() != null) {
                Studio old = studioService.getStudioById(studio.getId());
                if (old != null) studio.setImageUrl(old.getImageUrl());
            }

            studioService.saveStudio(studio);

            if (!audioFile.isEmpty()) {
                String audioName = saveFile(audioFile, "audio_");
                StudioDemo demo = new StudioDemo();
                demo.setTrackName("Demo - " + studio.getName());
                demo.setAudioUrl("/uploads/" + audioName);
                demo.setStudio(studio);
                studioDemoRepo.save(demo);
            }
            ra.addFlashAttribute("message", "Lưu phòng thu thành công!");
        } catch (IOException e) {
            ra.addFlashAttribute("error", "Lỗi upload file: " + e.getMessage());
        }
        return "redirect:/admin/studios";
    }

    @GetMapping("/studio/delete/{id}")
    public String deleteStudio(@PathVariable Long id, RedirectAttributes ra) {
        try {
            studioService.deleteStudio(id);
            ra.addFlashAttribute("message", "Đã xóa phòng thu!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Không thể xóa (có ràng buộc dữ liệu).");
        }
        return "redirect:/admin/studios";
    }

    // ========================================================================
    // 4. QUẢN LÝ BÀI VIẾT (POSTS)
    // ========================================================================
    @GetMapping("/posts")
    public String postManager(Model model,
                              @RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(defaultValue = "0") int page) {
        int pageSize = 5;
        Page<Post> pageResult = StringUtils.hasText(keyword)
                ? postService.searchPosts(keyword, page, pageSize)
                : postService.getAllPosts(page, pageSize);

        model.addAttribute("posts", pageResult.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageResult.getTotalPages());
        model.addAttribute("keyword", keyword);

        model.addAttribute("activePage", "posts"); // Highlight menu Posts
        return "admin/post-manager";
    }

    @GetMapping("/post/new")
    public String showPostForm(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("activePage", "posts");
        return "admin/post-form";
    }

    @GetMapping("/post/edit/{id}")
    public String showEditPostForm(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.getPostById(id));
        model.addAttribute("activePage", "posts");
        return "admin/post-form";
    }

    @PostMapping("/post/save")
    public String savePost(@ModelAttribute("post") Post post,
                           @RequestParam("imageFile") MultipartFile imageFile,
                           RedirectAttributes ra) {
        try {
            if (!imageFile.isEmpty()) {
                String fileName = saveFile(imageFile, "blog_");
                post.setImageUrl("/uploads/" + fileName);
            } else if (post.getId() != null) {
                Post oldPost = postService.getPostById(post.getId());
                if (oldPost != null) post.setImageUrl(oldPost.getImageUrl());
            }

            if (post.getId() == null) post.setCreatedAt(LocalDateTime.now());
            postService.savePost(post);
            ra.addFlashAttribute("message", "Lưu bài viết thành công!");
        } catch (IOException e) {
            ra.addFlashAttribute("error", "Lỗi upload ảnh.");
        }
        return "redirect:/admin/posts";
    }

    @GetMapping("/post/delete/{id}")
    public String deletePost(@PathVariable Long id, RedirectAttributes ra) {
        postService.deletePost(id);
        ra.addFlashAttribute("message", "Đã xóa bài viết.");
        return "redirect:/admin/posts";
    }

    // ========================================================================
    // 5. QUẢN LÝ ĐẶT LỊCH (BOOKINGS)
    // ========================================================================
    @GetMapping("/bookings")
    public String bookingManager(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 10;
        Page<Booking> pageResult = bookingService.getAllBookings(page, pageSize);
        model.addAttribute("bookings", pageResult.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageResult.getTotalPages());

        model.addAttribute("activePage", "bookings"); // Highlight menu Bookings
        return "admin/booking-manager";
    }

    @GetMapping("/booking/approve/{id}")
    public String approveBooking(@PathVariable Long id, RedirectAttributes ra) {
        bookingService.updateBookingStatus(id, "CONFIRMED");
        ra.addFlashAttribute("message", "Đã xác nhận đơn #" + id);
        return "redirect:/admin/bookings";
    }

    @GetMapping("/booking/reject/{id}")
    public String rejectBooking(@PathVariable Long id, RedirectAttributes ra) {
        bookingService.updateBookingStatus(id, "CANCELLED");
        ra.addFlashAttribute("message", "Đã hủy đơn #" + id);
        return "redirect:/admin/bookings";
    }

    @GetMapping("/booking/complete/{id}")
    public String completeBooking(@PathVariable Long id, RedirectAttributes ra) {
        bookingService.updateBookingStatus(id, "COMPLETED");
        ra.addFlashAttribute("message", "Đơn #" + id + " hoàn thành.");
        return "redirect:/admin/bookings";
    }

    // ========================================================================
    // 6. QUẢN LÝ THIẾT BỊ (EQUIPMENTS)
    // ========================================================================
    @GetMapping("/equipments")
    public String equipmentManager(Model model,
                                   @RequestParam(value = "keyword", required = false) String keyword,
                                   @RequestParam(defaultValue = "0") int page) {
        int pageSize = 8;
        Page<Equipment> pageResult;

        if (StringUtils.hasText(keyword)) {
            pageResult = equipmentService.searchEquipments(keyword, page, pageSize);
        } else {
            pageResult = equipmentService.getAllEquipments(page, pageSize);
        }

        model.addAttribute("equipments", pageResult.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageResult.getTotalPages());
        model.addAttribute("keyword", keyword);

        model.addAttribute("activePage", "equipments"); // Highlight menu Equipments
        return "admin/equipment-list";
    }

    @GetMapping("/equipment/new")
    public String showCreateEquipmentForm(Model model) {
        model.addAttribute("equipment", new Equipment());
        model.addAttribute("pageTitle", "Thêm Thiết Bị Mới");
        model.addAttribute("activePage", "equipments");
        return "admin/equipment-form";
    }

    @GetMapping("/equipment/edit/{id}")
    public String showEditEquipmentForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        Equipment equipment = equipmentService.getEquipmentById(id);
        if (equipment == null) {
            ra.addFlashAttribute("error", "Không tìm thấy thiết bị!");
            return "redirect:/admin/equipments";
        }
        model.addAttribute("equipment", equipment);
        model.addAttribute("pageTitle", "Sửa Thiết Bị: " + equipment.getName());
        model.addAttribute("activePage", "equipments");
        return "admin/equipment-form";
    }

    @PostMapping("/equipment/save")
    public String saveEquipment(@ModelAttribute Equipment equipment,
                                @RequestParam("imageFile") MultipartFile imageFile, // Nhận file từ form
                                RedirectAttributes ra) {
        try {
            // Xử lý lưu file ảnh nếu người dùng có chọn ảnh mới
            if (!imageFile.isEmpty()) {
                String fileName = saveFile(imageFile, "equip_"); // Sử dụng hàm saveFile dùng chung
                equipment.setImageUrl("/uploads/" + fileName);
            } else if (equipment.getId() != null) {
                // Nếu không chọn ảnh mới, giữ nguyên ảnh cũ
                Equipment old = equipmentService.getEquipmentById(equipment.getId());
                if (old != null) equipment.setImageUrl(old.getImageUrl());
            }

            equipmentService.saveEquipment(equipment);
            ra.addFlashAttribute("message", "Lưu thiết bị thành công!");
        } catch (IOException e) {
            ra.addFlashAttribute("error", "Lỗi upload ảnh: " + e.getMessage());
        }
        return "redirect:/admin/equipments";
    }

    @GetMapping("/equipment/delete/{id}")
    public String deleteEquipment(@PathVariable Long id, RedirectAttributes ra) {
        try {
            equipmentService.deleteEquipment(id);
            ra.addFlashAttribute("message", "Đã xóa thiết bị!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Không thể xóa (có đơn hàng liên quan).");
        }
        return "redirect:/admin/equipments";
    }

    // ========================================================================
    // 7. XỬ LÝ KHÁC
    // ========================================================================

    @GetMapping("/demo/delete/{id}")
    public String deleteDemo(@PathVariable Long id, RedirectAttributes ra) {
        Long studioId = studioService.deleteStudioDemo(id);
        if (studioId != null) {
            ra.addFlashAttribute("message", "Đã xóa bản thu mẫu!");
            return "redirect:/admin/studio/edit/" + studioId;
        } else {
            ra.addFlashAttribute("error", "Không tìm thấy file xóa!");
            return "redirect:/admin/studios";
        }
    }

    private String saveFile(MultipartFile file, String prefix) throws IOException {
        if (file.isEmpty()) return null;
        if (!Files.exists(rootLocation)) Files.createDirectories(rootLocation);

        String originalName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileName = System.currentTimeMillis() + "_" + prefix + originalName.replaceAll("\\s+", "_");

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        }
        return fileName;
    }
}