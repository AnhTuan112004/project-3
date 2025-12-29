package K23CNT1.natProject3.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "nat_roles") //
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nat_role_id") //
    private Long id;

    @Column(name = "nat_role_name", nullable = false) //
    private String name; // ROLE_ADMIN, ROLE_USER, ROLE_STAFF
}