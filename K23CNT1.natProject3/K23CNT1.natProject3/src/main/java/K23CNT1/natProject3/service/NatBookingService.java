package K23CNT1.natProject3.service;

import K23CNT1.natProject3.dto.BookingRequestDTO;
import K23CNT1.natProject3.entity.*;
import K23CNT1.natProject3.repository.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class NatBookingService {

    @Autowired private NatBookingRepository bookingRepo;
    @Autowired private NatRoomRepository roomRepo;
    @Autowired private NatUserRepository userRepo;
    @Autowired private NatEquipmentRepository equipmentRepo;
    @Autowired private NatBookingEquipmentRepository bookingEquipmentRepo;
    @Autowired private NatEmailService emailService; // Service gửi mail bên dưới

    // --- PHẦN 1: TẠO ĐƠN ĐẶT LỊCH ---
    @Transactional // Đảm bảo toàn vẹn dữ liệu: Lỗi là rollback hết
    public NatBooking createBooking(Long userId, BookingRequestDTO request) {

        // 1. Validate dữ liệu
        NatUser user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        NatRoom room = roomRepo.findById(request.getRoomId()).orElseThrow(() -> new RuntimeException("Room not found"));

        if (request.getEndTime().isBefore(request.getStartTime())) {
            throw new RuntimeException("Thời gian kết thúc phải sau thời gian bắt đầu");
        }

        // 2. Tính toán thời gian (Số giờ)
        long hours = Duration.between(request.getStartTime(), request.getEndTime()).toHours();
        if (hours < 1) hours = 1; // Tối thiểu tính 1 tiếng
        // Nếu dư phút (ví dụ 1h15p) -> làm tròn lên 2 tiếng
        if (Duration.between(request.getStartTime(), request.getEndTime()).toMinutes() % 60 > 0) {
            hours++;
        }
        BigDecimal totalHours = new BigDecimal(hours);

        // 3. Khởi tạo Booking Header
        NatBooking booking = new NatBooking();
        booking.setNatUser(user);
        booking.setNatRoom(room);
        booking.setNatstartTime(request.getStartTime());
        booking.setNatendTime(request.getEndTime());
        booking.setNatstatus("PENDING"); // Mặc định chờ duyệt

        // Tạm tính tiền phòng: Giá phòng * Số giờ
        BigDecimal roomTotal = room.getNatprice().multiply(totalHours);

        // Lưu booking lần 1 để lấy ID
        NatBooking savedBooking = bookingRepo.save(booking);

        // 4. Xử lý Thiết bị thuê kèm (Nếu có)
        BigDecimal equipmentTotal = BigDecimal.ZERO;

        if (request.getSelectedEquipmentIds() != null && !request.getSelectedEquipmentIds().isEmpty()) {
            List<NatEquipment> equipments = equipmentRepo.findAllById(request.getSelectedEquipmentIds());
            List<NatBookingEquipment> details = new ArrayList<>();

            for (NatEquipment eq : equipments) {
                NatBookingEquipment detail = new NatBookingEquipment();
                detail.setNatBooking(savedBooking);
                detail.setNatEquipment(eq);
                detail.setNatquantity(1); // Mặc định số lượng 1
                detail.setNatpriceAtBooking(eq.getNatprice()); // Lưu giá tại thời điểm đặt

                // Tính tiền thiết bị: Giá * Số giờ
                BigDecimal itemCost = eq.getNatprice().multiply(totalHours);
                equipmentTotal = equipmentTotal.add(itemCost);

                details.add(detail);
            }
            // Lưu danh sách thiết bị vào DB
            bookingEquipmentRepo.saveAll(details);
        }

        // 5. Cập nhật Tổng tiền cuối cùng & Lưu lại
        savedBooking.setNattotalAmount(roomTotal.add(equipmentTotal));
        NatBooking finalBooking = bookingRepo.save(savedBooking);

        // 6. Gửi Email xác nhận (Chạy luồng riêng để không làm lag web)
        new Thread(() -> emailService.sendBookingConfirmation(finalBooking)).start();

        return finalBooking;
    }

    // --- PHẦN 2: QUẢN LÝ & THỐNG KÊ (ADMIN) ---

    // Lấy danh sách đơn hàng (Phân trang)
    public Page<NatBooking> getAllBookings(int page, int size) {
        return bookingRepo.findAllByOrderByNatcreatedAtDesc(PageRequest.of(page - 1, size));
    }

    // Lấy chi tiết đơn hàng theo ID
    public NatBooking getBookingById(Long id) {
        return bookingRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));
    }

    // Cập nhật trạng thái đơn (Duyệt, Hủy, Hoàn thành)
    public void updateBookingStatus(Long id, String status) {
        NatBooking booking = getBookingById(id);
        booking.setNatstatus(status);
        bookingRepo.save(booking);
    }

    // Lấy dữ liệu biểu đồ doanh thu (Trả về mảng 12 tháng)
    public List<Long> getRevenueData() {
        List<Object[]> results = bookingRepo.getMonthlyRevenue();
        // Tạo list 12 số 0
        List<Long> revenues = new ArrayList<>(Collections.nCopies(12, 0L));

        for (Object[] row : results) {
            int month = (int) row[0]; // Tháng (1-12)
            double amount = ((Number) row[1]).doubleValue(); // Tổng tiền
            revenues.set(month - 1, (long) amount);
        }
        return revenues;
    }

    // Lấy dữ liệu biểu đồ tròn (Tỷ lệ phòng)
    public List<Object[]> getRoomStats() {
        return bookingRepo.getRoomBookingStats();
    }
}