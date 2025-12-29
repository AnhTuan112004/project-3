package K23CNT1.natProject3.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "nat_studio_demos") //
@Data
public class StudioDemo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nat_demo_id") //
    private Long id;

    @Column(name = "nat_track_name") //
    private String trackName;

    @Column(name = "nat_audio_url") //
    private String audioUrl;

    @ManyToOne
    @JoinColumn(name = "nat_studio_id") //
    private Studio studio;
}