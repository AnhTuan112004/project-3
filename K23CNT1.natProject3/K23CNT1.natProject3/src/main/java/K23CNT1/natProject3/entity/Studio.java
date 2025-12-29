package K23CNT1.natProject3.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "nat_studios") //
@Data
public class Studio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nat_studio_id") //
    private Long id;

    @Column(name = "nat_name", nullable = false) //
    private String name;

    @Column(name = "nat_description", columnDefinition = "TEXT") //
    private String description;

    @Column(name = "nat_type") //
    private String studioType; // VOCAL, LIVE_ROOM

    @Column(name = "nat_price_per_hour") //
    private BigDecimal pricePerHour;

    @Column(name = "nat_image_url") //
    private String imageUrl;

    @Column(name = "nat_status") //
    private String status; // ACTIVE, MAINTENANCE

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<StudioDemo> demos;
}