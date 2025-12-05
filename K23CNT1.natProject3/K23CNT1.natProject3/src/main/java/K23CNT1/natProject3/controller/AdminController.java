package K23CNT1.natProject3.controller;


import K23CNT1.natProject3.entity.NatRoom;
import K23CNT1.natProject3.repository.NatRoomTypeRepository;
import K23CNT1.natProject3.service.NatBookingService;
import K23CNT1.natProject3.service.NatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private NatRoomService roomService;
    @Autowired private NatBookingService bookingService;
    @Autowired private NatRoomTypeRepository roomTypeRepo;

    // 1. Dashboard Thống kê
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Dữ liệu Doanh thu (Mảng 12 tháng)
        model.addAttribute("revenueData", bookingService.getRevenueData());

        // Dữ liệu Biểu đồ tròn (Phòng)
        List<Object[]> roomStats = bookingService.getRoomStats();
        List<String> roomNames = new ArrayList<>();
        List<Long> roomCounts = new ArrayList<>();

        for (Object[] row : roomStats) {
            roomNames.add((String) row[0]);
            roomCounts.add(((Number) row[1]).longValue());
        }
        model.addAttribute("roomNames", roomNames);
        model.addAttribute("roomCounts", roomCounts);

        return "admin/dashboard";
    }

    // 2. Quản lý danh sách phòng
    @GetMapping("/rooms")
    public String listRooms(Model model,
                            @RequestParam(name = "keyword", required = false) String keyword,
                            @RequestParam(name = "page", defaultValue = "1") int page) {
        Page<NatRoom> listRooms = roomService.getAllRooms(keyword, page, 10);
        model.addAttribute("rooms", listRooms);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        return "admin/rooms-list";
    }

    // 3. Form thêm phòng mới
    @GetMapping("/rooms/new")
    public String newRoomForm(Model model) {
        model.addAttribute("room", new NatRoom());
        model.addAttribute("types", roomTypeRepo.findAll()); // Dropdown loại phòng
        return "admin/room-form";
    }

    // 4. Lưu phòng
    @PostMapping("/rooms/save")
    public String saveRoom(@ModelAttribute NatRoom room) {
        roomService.saveRoom(room);
        return "redirect:/admin/rooms";
    }

    // 5. Xóa phòng
    @GetMapping("/rooms/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return "redirect:/admin/rooms";
    }
}