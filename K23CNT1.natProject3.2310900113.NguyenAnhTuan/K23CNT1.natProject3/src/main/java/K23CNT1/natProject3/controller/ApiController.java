package K23CNT1.natProject3.controller;


import K23CNT1.natProject3.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController // Quan trọng: Trả về Data thay vì View
@RequestMapping("/api")
public class ApiController {

    @Autowired private BookingService bookingService;

    // URL này sẽ được gọi bởi Ajax trong file studio-detail.html
    @GetMapping("/calendar/events")
    public List<Map<String, Object>> getCalendarEvents(@RequestParam Long studioId) {
        return bookingService.getCalendarEvents(studioId);
    }
}