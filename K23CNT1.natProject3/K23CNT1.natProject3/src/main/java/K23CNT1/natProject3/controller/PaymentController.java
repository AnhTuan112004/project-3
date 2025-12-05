package K23CNT1.natProject3.controller;


import K23CNT1.natProject3.entity.NatBooking;
import K23CNT1.natProject3.service.NatBookingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired private PaymentService paymentService;
    @Autowired private NatBookingService bookingService;
    @Autowired private VNPayConfig vnPayConfig;

    @GetMapping("/create/{bookingId}")
    public String createPayment(@PathVariable Long bookingId, HttpServletRequest request) {
        NatBooking booking = bookingService.getBookingById(bookingId);
        long amount = booking.getNattotalAmount().longValue();
        String orderInfo = "Thanh toan don hang " + bookingId;

        String url = paymentService.createPaymentUrl(amount, orderInfo, vnPayConfig.getIpAddress(request));
        return "redirect:" + url;
    }

    @GetMapping("/vnpay-return")
    public String returnUrl(HttpServletRequest request, Model model) {
        String responseCode = request.getParameter("vnp_ResponseCode");
        String orderInfo = request.getParameter("vnp_OrderInfo"); // "Thanh toan don hang 123"

        if ("00".equals(responseCode)) {
            // Lấy ID đơn hàng từ orderInfo
            Long bookingId = Long.parseLong(orderInfo.replace("Thanh toan don hang ", ""));

            // Cập nhật DB
            bookingService.updateBookingStatus(bookingId, "DEPOSITED");

            model.addAttribute("message", "Thanh toán thành công!");
            return "client/payment-success";
        } else {
            model.addAttribute("message", "Thanh toán thất bại.");
            return "client/payment-failed";
        }
    }
}