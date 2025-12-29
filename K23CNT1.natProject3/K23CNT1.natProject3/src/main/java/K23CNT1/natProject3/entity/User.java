package K23CNT1.natProject3.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "nat_users") //
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nat_user_id") //
    private Long id;

    @Column(name = "nat_username", unique = true, nullable = false) //
    private String username;

    @Column(name = "nat_password", nullable = false) //
    private String password;

    @Column(name = "nat_fullname") //
    private String fullName;

    @Column(name = "nat_email") //
    private String email;

    @Column(name = "nat_phone") //
    private String phone;

    @Column(name = "nat_total_spending") //
    private BigDecimal totalSpending = BigDecimal.ZERO;

    @Column(name = "nat_rank_level") //
    private String rankLevel = "BRONZE"; // BRONZE, SILVER, GOLD

    @ManyToOne
    @JoinColumn(name = "nat_role_id") //
    private Role role;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Booking> bookings;
}