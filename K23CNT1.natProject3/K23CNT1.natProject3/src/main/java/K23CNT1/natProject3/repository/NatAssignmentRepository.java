package K23CNT1.natProject3.repository;


import K23CNT1.natProject3.entity.NatAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NatAssignmentRepository extends JpaRepository<NatAssignment, Long> {
    // Lấy danh sách công việc của 1 nhân viên cụ thể (Staff Dashboard)
    List<NatAssignment> findByNatStaff_NatidAndNatstatus(Long staffId, String status);

    // Tìm việc theo mã đơn
    NatAssignment findByNatBooking_Natid(Long bookingId);
}