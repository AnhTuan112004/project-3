package K23CNT1.natDay07lap08.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "nat_book_author") @Data
public class NatBookAuthor {
    @EmbeddedId
    private NatBookAuthorId natId = new NatBookAuthorId();

    @ManyToOne @MapsId("natBookId")
    @JoinColumn(name = "nat_book_id")
    @EqualsAndHashCode.Exclude @ToString.Exclude // Fix lỗi
    private NatBook natBook;

    @ManyToOne @MapsId("natAuthorId")
    @JoinColumn(name = "nat_author_id")
    @EqualsAndHashCode.Exclude @ToString.Exclude // Fix lỗi
    private NatAuthor natAuthor;

    @Column(name = "nat_is_editor")
    private Boolean natIsEditor;
}