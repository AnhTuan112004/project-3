package K23CNT1.natProject3.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "natassignments")
@Data @NoArgsConstructor @AllArgsConstructor
public class NatAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natid;

    private String natstatus; // ASSIGNED, DONE

    @Column(columnDefinition = "TEXT")
    private String natnote;

    // Phân công cho đơn hàng nào?
    @ManyToOne
    @JoinColumn(name = "natbooking_id")
    private NatBooking natBooking;

    // Phân công cho nhân viên nào?
    @ManyToOne
    @JoinColumn(name = "natstaff_id")
    private NatUser natStaff;
}