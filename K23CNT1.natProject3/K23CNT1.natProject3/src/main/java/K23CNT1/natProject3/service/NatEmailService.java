package K23CNT1.natProject3.service;


import K23CNT1.natProject3.entity.NatBooking;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class NatEmailService {

    @Autowired private JavaMailSender mailSender;
    @Autowired private TemplateEngine templateEngine; // Thư viện Thymeleaf để render HTML

    public void sendBookingConfirmation(NatBooking booking) {
        // 1. Đổ dữ liệu vào template HTML
        Context context = new Context();
        context.setVariable("name", booking.getNatUser().getNatfullname());
        context.setVariable("bookingId", booking.getNatid());
        context.setVariable("roomName", booking.getNatRoom().getNatname());
        context.setVariable("startTime", booking.getNatstartTime());
        context.setVariable("total", booking.getNattotalAmount());

        // 'mail/email-template' là tên file trong resources/templates/mail/
        String htmlContent = templateEngine.process("mail/email-template", context);

        // 2. Gửi mail
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(booking.getNatUser().getNatusername()); // Giả sử username là email
            helper.setSubject("NatStudio - Xác nhận đơn hàng #" + booking.getNatid());
            helper.setText(htmlContent, true); // true = Bật chế độ HTML

            mailSender.send(message);
            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }

    public void sendBookingConfirmation(NatBooking finalBooking) {
    }
}