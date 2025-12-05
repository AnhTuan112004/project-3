package K23CNT1.natProject3.controller;


import K23CNT1.natProject3.entity.NatBooking;
import K23CNT1.natProject3.service.NatBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/bookings")
public class AdminBookingController {

    @Autowired private NatBookingService bookingService;

    // Danh sách đơn hàng
    @GetMapping
    public String listBookings(Model model, @RequestParam(defaultValue = "1") int page) {
        Page<NatBooking> bookings = bookingService.getAllBookings(page, 10);
        model.addAttribute("bookings", bookings);
        model.addAttribute("currentPage", page);
        return "admin/bookings-list";
    }

    // Xem chi tiết đơn (Kèm thiết bị)
    @GetMapping("/detail/{id}")
    public String viewDetail(@PathVariable Long id, Model model) {
        model.addAttribute("booking", bookingService.getBookingById(id));
        return "admin/booking-detail";
    }

    // Duyệt cọc (Chuyển sang DEPOSITED)
    @GetMapping("/approve/{id}")
    public String approve(@PathVariable Long id) {
        bookingService.updateBookingStatus(id, "DEPOSITED");
        return "redirect:/admin/bookings";
    }

    // Hoàn thành (Chuyển sang COMPLETED)
    @GetMapping("/complete/{id}")
    public String complete(@PathVariable Long id) {
        bookingService.updateBookingStatus(id, "COMPLETED");
        return "redirect:/admin/bookings";
    }

    // Hủy đơn
    @GetMapping("/cancel/{id}")
    public String cancel(@PathVariable Long id) {
        bookingService.updateBookingStatus(id, "CANCELLED");
        return "redirect:/admin/bookings";
    }
}