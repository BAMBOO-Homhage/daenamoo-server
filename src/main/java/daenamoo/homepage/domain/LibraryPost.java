package daenamoo.homepage.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class LibraryPost {

    @Id @GeneratedValue
    @Column(name = "library_post_id")
    private Long id;

    private String title;
    private String link;
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "libraryPost")
    private List<LibraryPostCatrgory> categories = new ArrayList<>();

}
