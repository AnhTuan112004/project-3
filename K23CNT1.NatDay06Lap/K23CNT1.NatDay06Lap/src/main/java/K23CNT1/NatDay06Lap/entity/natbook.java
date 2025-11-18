package K23CNT1.NatDay06Lap.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "natbook")
@Data @Builder @AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class natbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nat_id") // ✅ Khóa chính: nat_id
    private Long natid;

    @Column(name = "natcode", unique = true, nullable = false)
    private String natcode;

    @Column(name = "natname", nullable = false)
    private String natname;

    @Column(name = "natdescription")
    private String natdescription;

    @Column(name = "natimg_url")
    private String natimgUrl;

    @Column(name = "natquantity")
    private Integer natquantity;

    @Column(name = "natprice")
    private Double natprice;

    @Column(name = "natis_active")
    private Boolean natisActive = true;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<natBookAuthor> bookAuthors = new ArrayList<>();
}