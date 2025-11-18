package K23CNT1.NatDay06Lap.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "natauthor") // Tên bảng trong DB
@Data @Builder @AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class natauthor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // ✅ ĐÃ SỬA: Phải là nat_id để nhất quán với natbook và natBookAuthor
    @Column(name = "nat_id")
    private Long natid;

    @Column(name = "natcode", unique = true, nullable = false)
    private String natcode;

    @Column(name = "natname", nullable = false)
    private String natname;

    @Column(name = "natdescription")
    private String natdescription;

    // ✅ ĐÃ SỬA: Ánh xạ tới natimg_url
    @Column(name = "natimg_url")
    private String natimgUrl;

    @Column(name = "natemail")
    private String natemail;

    @Column(name = "natphone")
    private String natphone;

    @Column(name = "nataddress")
    private String nataddress;

    // ✅ ĐÃ SỬA: Ánh xạ tới natis_active
    @Column(name = "natis_active")
    private Boolean natisActive = true;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<natBookAuthor> bookAuthors = new ArrayList<>();
}