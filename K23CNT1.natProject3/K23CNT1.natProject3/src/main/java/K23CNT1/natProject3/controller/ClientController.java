package K23CNT1.natProject3.controller;


import K23CNT1.natProject3.dto.BookingRequestDTO;
import K23CNT1.natProject3.entity.NatBooking;
import K23CNT1.natProject3.entity.NatRoom;
import K23CNT1.natProject3.entity.NatUser;
import K23CNT1.natProject3.repository.NatReviewRepository;
import K23CNT1.natProject3.service.NatBookingService;
import K23CNT1.natProject3.service.NatEquipmentService;
import K23CNT1.natProject3.service.NatRoomService;
import K23CNT1.natProject3.service.NatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class ClientController {

    @Autowired private NatRoomService roomService;
    @Autowired private NatBookingService bookingService;
    @Autowired private NatEquipmentService equipmentService;
    @Autowired private NatUserService userService;
    @Autowired private NatReviewRepository reviewRepo;

    // 1. Trang chủ (Danh sách phòng)
    @GetMapping("/")
    public String home(Model model,
                       @RequestParam(name = "keyword", required = false) String keyword,
                       @RequestParam(name = "page", defaultValue = "1") int page) {
        Page<NatRoom> listRooms = roomService.getAllRooms(keyword, page, 6);
        model.addAttribute("listRooms", listRooms);
        model.addAttribute("keyword", keyword);
        return "client/home";
    }

    // 2. Chi tiết phòng & Review
    @GetMapping("/room/{id}")
    public String roomDetail(@PathVariable Long id, Model model) {
        NatRoom room = roomService.getRoomById(id);
        model.addAttribute("room", room);
        // Lấy đánh giá của phòng này
        model.addAttribute("reviews", reviewRepo.findByNatRoom(room));
        return "client/room-detail";
    }

    // 3. Form đặt lịch (Cần đăng nhập)
    @GetMapping("/booking/{id}")
    public String bookingForm(@PathVariable Long id, Model model) {
        model.addAttribute("room", roomService.getRoomById(id));
        // Lấy danh sách thiết bị để khách chọn
        model.addAttribute("equipments", equipmentService.getAllEquipments());

        BookingRequestDTO dto = new BookingRequestDTO();
        dto.setRoomId(id);
        model.addAttribute("bookingDTO", dto);

        return "client/booking-form";
    }

    // 4. Xử lý đặt lịch
    @PostMapping("/booking/create")
    public String createBooking(@ModelAttribute BookingRequestDTO bookingDTO,
                                Principal principal,
                                Model model) {
        try {
            // Lấy User đang đăng nhập
            NatUser currentUser = userService.findByUsername(principal.getName());

            // Gọi Service tạo đơn
            NatBooking newBooking = bookingService.createBooking(currentUser.getNatid(), bookingDTO);

            return "redirect:/booking/success/" + newBooking.getNatid();
        } catch (Exception e) {
            e.printStackTrace();
            // Nếu lỗi thì quay lại form và báo lỗi
            model.addAttribute("error", "Lỗi: " + e.getMessage());
            model.addAttribute("room", roomService.getRoomById(bookingDTO.getRoomId()));
            model.addAttribute("equipments", equipmentService.getAllEquipments());
            return "client/booking-form";
        }
    }

    // 5. Trang thông báo thành công
    @GetMapping("/booking/success/{id}")
    public String bookingSuccess(@PathVariable Long id, Model model) {
        model.addAttribute("booking", bookingService.getBookingById(id));
        return "client/booking-success";
    }

    // 6. Trang xem Lịch (Visual Calendar)
    @GetMapping("/calendar")
    public String viewCalendar() {
        return "client/calendar-view";
    }
}