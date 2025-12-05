package K23CNT1.natProject3.controller;


import K23CNT1.natProject3.dto.CalendarEventDTO;
import K23CNT1.natProject3.entity.NatBooking;
import K23CNT1.natProject3.repository.NatBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/calendar")
public class CalendarApiController {

    @Autowired private NatBookingRepository bookingRepo;

    @GetMapping("/events")
    public List<CalendarEventDTO> getEvents() {
        List<NatBooking> bookings = bookingRepo.findAll();
        List<CalendarEventDTO> events = new ArrayList<>();

        for (NatBooking b : bookings) {
            if (!"CANCELLED".equals(b.getNatstatus())) {
                String color = "#3788d8"; // Mặc định xanh dương
                if ("PENDING".equals(b.getNatstatus())) color = "#f6c23e"; // Vàng
                if ("COMPLETED".equals(b.getNatstatus())) color = "#1cc88a"; // Xanh lá

                events.add(new CalendarEventDTO(
                        b.getNatRoom().getNatname(), // Title
                        b.getNatstartTime().toString(), // Start
                        b.getNatendTime().toString(), // End
                        color, // Color
                        "/room/" + b.getNatRoom().getNatid() // URL
                ));
            }
        }
        return events;
    }
}