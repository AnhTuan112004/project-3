package K23CNT1.natDay07lap08.entity;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable @Data @EqualsAndHashCode
public class NatBookAuthorId implements Serializable {
    private Long natBookId;
    private Long natAuthorId;
}