package K23CNT1.natDay07lap08.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity @Table(name = "nat_author") @Data
public class NatAuthor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nat_author_id")
    private Long natAuthorId;

    @Column(name = "nat_author_code") private String natAuthorCode;
    @Column(name = "nat_author_name") private String natAuthorName;

    @OneToMany(mappedBy = "natAuthor")
    @EqualsAndHashCode.Exclude @ToString.Exclude // Fix lỗi
    private Set<NatBookAuthor> natBookAuthors = new HashSet<>();
}