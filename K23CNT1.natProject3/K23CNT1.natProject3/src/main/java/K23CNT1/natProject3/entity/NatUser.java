package K23CNT1.natProject3.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "natusers")
@Data @NoArgsConstructor @AllArgsConstructor
public class NatUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natid;

    @Column(nullable = false, unique = true)
    private String natusername;

    @Column(nullable = false)
    private String natpassword;

    private String natfullname;

    // Giá trị: 'ROLE_ADMIN', 'ROLE_STAFF', 'ROLE_USER'
    private String natrole;

    // (Tùy chọn) Để lấy danh sách đơn hàng của user này
    @OneToMany(mappedBy = "natUser")
    private List<NatBooking> natBookings;
}