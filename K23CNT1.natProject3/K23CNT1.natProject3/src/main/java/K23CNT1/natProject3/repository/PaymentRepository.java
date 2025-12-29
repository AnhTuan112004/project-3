package K23CNT1.natProject3.repository;

import K23CNT1.natProject3.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Tìm theo mã giao dịch hệ thống ngoài (nat_transaction_code)
    Optional<Payment> findByTransactionCode(String transactionCode);

    Payment findByBookingId(Long bookingId);
}