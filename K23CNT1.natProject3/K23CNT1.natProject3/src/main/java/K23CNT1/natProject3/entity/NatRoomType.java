package K23CNT1.natProject3.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "natroom_types")
@Data @NoArgsConstructor @AllArgsConstructor
public class NatRoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natid;

    @Column(nullable = false)
    private String natname;

    @Column(columnDefinition = "TEXT")
    private String natdescription;

    // Quan hệ 1 Loại phòng - N Phòng
    @OneToMany(mappedBy = "natRoomType")
    private List<NatRoom> natRooms;
}