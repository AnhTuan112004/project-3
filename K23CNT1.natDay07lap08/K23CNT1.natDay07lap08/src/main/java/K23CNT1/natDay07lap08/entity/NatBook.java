package K23CNT1.natDay07lap08.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity @Table(name = "nat_book") @Data
public class NatBook {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nat_book_id")
    private Long natBookId;

    @Column(name = "nat_book_code") private String natBookCode;
    @Column(name = "nat_book_name") private String natBookName;
    @Column(name = "nat_price") private Double natPrice;

    @OneToMany(mappedBy = "natBook", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude @ToString.Exclude // Fix lỗi ConcurrentModification
    private Set<NatBookAuthor> natBookAuthors = new HashSet<>();
}