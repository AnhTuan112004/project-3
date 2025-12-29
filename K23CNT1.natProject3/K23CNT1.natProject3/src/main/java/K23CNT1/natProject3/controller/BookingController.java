package K23CNT1.natProject3.controller;

import K23CNT1.natProject3.dto.BookingRequestDTO;
import K23CNT1.natProject3.entity.Booking;
import K23CNT1.natProject3.entity.User;
import K23CNT1.natProject3.repository.UserRepository;
import K23CNT1.natProject3.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // [1. Import quan trọng]

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

@Controller
public class BookingController {

    @Autowired private BookingService bookingService;
    @Autowired private UserRepository userRepo;

    // 1. Xử lý đặt phòng (POST từ Form)
    @PostMapping("/booking/create")
    public String createBooking(@ModelAttribute BookingRequestDTO request,
                                Principal principal,
                                RedirectAttributes redirectAttributes) { // [2. Thêm tham số này]
        try {
            if (principal == null) return "redirect:/login";

            String username = principal.getName();
            User currentUser = userRepo.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

            request.setUserId(currentUser.getId());

            // Lưu đơn hàng
            Booking newBooking = bookingService.createBooking(request);

            // Chuyển hướng sang trang thanh toán
            return "redirect:/booking/payment/" + newBooking.getId();

        } catch (Exception e) {
            // [3. SỬA LỖI UNICODE TẠI ĐÂY]
            // Thay vì cộng chuỗi thủ công gây lỗi Tomcat, dùng addAttribute để Spring tự mã hóa
            redirectAttributes.addAttribute("error", e.getMessage());

            return "redirect:/studios/" + request.getStudioId();
        }
    }

    // 2. Trang Thanh toán QR Code
    @GetMapping("/booking/payment/{id}")
    public String paymentPage(@PathVariable Long id, Model model, Principal principal) {
        if (principal == null) return "redirect:/login";

        Booking booking = bookingService.getBookingById(id);

        // Kiểm tra null booking
        if (booking == null) return "redirect:/home";

        // Bảo mật: Chỉ chủ đơn hàng mới xem được
        if (!booking.getUser().getUsername().equals(principal.getName())) {
            return "redirect:/home";
        }

        // Tạo nội dung chuyển khoản: NAT + ID + Tên
        // Ví dụ: NAT105_nguyenvana
        String rawContent = "NAT" + booking.getId() + "_" + principal.getName();

        // [4. Mã hóa nội dung QR]
        // Để tránh lỗi nếu tên user có ký tự đặc biệt hoặc khoảng trắng khi tạo link QR
        String encodedContent = URLEncoder.encode(rawContent, StandardCharsets.UTF_8);

        // Link VietQR (MB Bank)
        String qrUrl = "https://img.vietqr.io/image/MB-123456789-qr_only.png"
                + "?amount=" + booking.getTotalPrice().longValue() // Chuyển BigDecimal sang long để bỏ số thập phân
                + "&addInfo=" + encodedContent;

        model.addAttribute("booking", booking);
        model.addAttribute("qrUrl", qrUrl);
        model.addAttribute("paymentContent", rawContent); // Hiển thị bản chưa mã hóa cho người dùng dễ đọc

        return "payment"; // Cần tạo file payment.html
    }

    // 3. Xem lịch sử đặt phòng
    @GetMapping("/booking/history")
    public String history(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";

        String username = principal.getName();
        User currentUser = userRepo.findByUsername(username).orElse(null);

        if (currentUser != null) {
            model.addAttribute("bookings", bookingService.getBookingsByUser(currentUser.getId()));
        }

        return "history"; // Cần tạo file history.html
    }
}