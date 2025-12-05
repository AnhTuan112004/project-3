package K23CNT1.natProject3.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "natblogs")
@Data @NoArgsConstructor @AllArgsConstructor
public class NatBlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natid;

    private String nattitle;

    @Column(columnDefinition = "TEXT")
    private String natcontent;

    private LocalDateTime natcreatedAt = LocalDateTime.now();

    // Ai là người viết bài này?
    @ManyToOne
    @JoinColumn(name = "natauthor_id")
    private NatUser author;
}