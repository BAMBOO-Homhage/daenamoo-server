package daenamoo.homepage.domain;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
public class LibraryPostCatrgory {

    @Id @GeneratedValue
    @Column(name = "lpc_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "library_post_id")
    private LibraryPost libraryPost;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
