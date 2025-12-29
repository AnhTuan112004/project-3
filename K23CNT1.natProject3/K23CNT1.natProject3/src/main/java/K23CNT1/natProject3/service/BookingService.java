package K23CNT1.natProject3.service;

import K23CNT1.natProject3.dto.BookingRequestDTO;
import K23CNT1.natProject3.entity.*;
import K23CNT1.natProject3.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired private BookingRepository bookingRepo;
    @Autowired private StudioRepository studioRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private EquipmentRepository equipmentRepo;
    @Autowired private BookingEquipmentRepository bookingEquipRepo;
    @Autowired private UserService userService;

    // ==================== 1. NGHIỆP VỤ ĐẶT LỊCH (USER) ====================

    @Transactional(rollbackFor = Exception.class)
    public Booking createBooking(BookingRequestDTO request) throws Exception {
        // 1. Kiểm tra logic thời gian cơ bản
        if (request.getStartTime().isBefore(LocalDateTime.now())) {
            throw new Exception("Không thể đặt lịch trong quá khứ!");
        }
        if (request.getEndTime().isBefore(request.getStartTime())) {
            throw new Exception("Thời gian kết thúc phải sau thời gian bắt đầu!");
        }

        // 2. Check trùng lịch
        List<Booking> conflicts = bookingRepo.findConflictingBookings(
                request.getStudioId(), request.getStartTime(), request.getEndTime());

        if (!conflicts.isEmpty()) {
            throw new Exception("Khung giờ này đã có người đăng ký! Vui lòng chọn khung giờ khác.");
        }

        Studio studio = studioRepo.findById(request.getStudioId())
                .orElseThrow(() -> new Exception("Phòng thu không tồn tại!"));
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new Exception("Người dùng không tồn tại!"));

        // 3. Tính phí thuê phòng (Làm tròn lên theo tiếng)
        long minutes = Duration.between(request.getStartTime(), request.getEndTime()).toMinutes();
        double hours = Math.ceil(minutes / 60.0);
        if (hours < 1) hours = 1;

        BigDecimal roomPrice = studio.getPricePerHour().multiply(BigDecimal.valueOf(hours));

        // 4. Khởi tạo Booking
        Booking booking = new Booking();
        booking.setStudio(studio);
        booking.setUser(user);
        booking.setStartTime(request.getStartTime());
        booking.setEndTime(request.getEndTime());
        booking.setStatus("PENDING"); // Chờ thanh toán/duyệt
        booking.setCreatedAt(LocalDateTime.now());

        // Lưu trước để lấy ID
        Booking savedBooking = bookingRepo.save(booking);

        // 5. Tính phí thiết bị
        BigDecimal totalEquipmentPrice = BigDecimal.ZERO;
        if (request.getEquipments() != null) {
            List<BookingEquipment> equipmentList = new ArrayList<>();
            for (BookingRequestDTO.EquipmentRequest item : request.getEquipments()) {
                if (item.getEquipmentId() != null) {
                    Equipment eq = equipmentRepo.findById(item.getEquipmentId()).orElse(null);
                    if (eq != null) {
                        BookingEquipment be = new BookingEquipment();
                        be.setBooking(savedBooking);
                        be.setEquipment(eq);
                        be.setQuantity(item.getQuantity() > 0 ? item.getQuantity() : 1);

                        BigDecimal itemSubtotal = eq.getRentPrice().multiply(BigDecimal.valueOf(be.getQuantity()));
                        totalEquipmentPrice = totalEquipmentPrice.add(itemSubtotal);
                        equipmentList.add(be);
                    }
                }
            }
            if (!equipmentList.isEmpty()) {
                bookingEquipRepo.saveAll(equipmentList);
            }
        }

        // 6. Áp dụng Rank Discount (Feature 4)
        BigDecimal subTotal = roomPrice.add(totalEquipmentPrice);
        BigDecimal discountRate = BigDecimal.ONE;

        // Dùng switch expression (Java 17+) hoặc if-else cho Rank
        String rank = (user.getRankLevel() != null) ? user.getRankLevel().toUpperCase() : "BRONZE";
        switch (rank) {
            case "GOLD":   discountRate = new BigDecimal("0.90"); break; // Giảm 10%
            case "SILVER": discountRate = new BigDecimal("0.95"); break; // Giảm 5%
            default:       discountRate = BigDecimal.ONE;
        }

        BigDecimal finalPrice = subTotal.multiply(discountRate).setScale(0, RoundingMode.HALF_UP);
        savedBooking.setTotalPrice(finalPrice);

        // Tiền cọc giữ chỗ (mặc định 30%)
        BigDecimal deposit = finalPrice.multiply(new BigDecimal("0.3")).setScale(0, RoundingMode.HALF_UP);
        savedBooking.setDeposit(deposit);

        return bookingRepo.save(savedBooking);
    }

    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepo.findByUserIdOrderByStartTimeDesc(userId);
    }

    public Booking getBookingById(Long id) {
        return bookingRepo.findById(id).orElse(null);
    }

    // ==================== 2. NGHIỆP VỤ QUẢN TRỊ (ADMIN) ====================

    public List<Booking> getAllBookings() {
        return bookingRepo.findAllByOrderByIdDesc();
    }

    @Transactional
    public void updateBookingStatus(Long bookingId, String status) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn đặt lịch!"));

        booking.setStatus(status.toUpperCase());
        bookingRepo.save(booking);

        // Nếu đơn hoàn thành, cập nhật hạng thành viên của khách hàng
        if ("COMPLETED".equalsIgnoreCase(status)) {
            userService.updateUserRank(booking.getUser().getId(), booking.getTotalPrice());
        }
    }

    // ==================== 3. TIỆN ÍCH LỊCH (FullCalendar) ====================

    public List<Map<String, Object>> getCalendarEvents(Long studioId) {
        List<Booking> bookings = bookingRepo.findByStudioId(studioId);

        return bookings.stream()
                .filter(b -> !"CANCELLED".equals(b.getStatus())) // Không hiện lịch đã hủy
                .map(b -> {
                    Map<String, Object> event = new HashMap<>();
                    event.put("id", b.getId());
                    event.put("title", "Đã đặt (" + b.getStatus() + ")");
                    event.put("start", b.getStartTime().toString()); // ISO format cho JS
                    event.put("end", b.getEndTime().toString());

                    // Logic màu sắc
                    switch (b.getStatus()) {
                        case "COMPLETED":
                        case "CONFIRMED":
                            event.put("color", "#28a745"); break;
                        case "PENDING":
                            event.put("color", "#ffc107");
                            event.put("textColor", "#000"); break;
                        default:
                            event.put("color", "#6c757d");
                    }
                    return event;
                }).collect(Collectors.toList());
    }
    // Import thêm Page, PageRequest, Pageable...

    // Thêm hàm lấy booking có phân trang
    public Page<Booking> getAllBookings(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return bookingRepo.findAll(pageable); // Hoặc findAllByOrderByCreatedAtDesc
    }
}