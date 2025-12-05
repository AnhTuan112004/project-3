package K23CNT1.natProject3.repository;


import K23CNT1.natProject3.entity.NatPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NatPaymentRepository extends JpaRepository<NatPayment, Long> {
    // Xem lịch sử thanh toán của 1 đơn hàng
    List<NatPayment> findByNatBooking_Natid(Long bookingId);
}