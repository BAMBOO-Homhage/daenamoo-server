package daenamoo.homepage.domain.post;


import daenamoo.homepage.domain.Comment;
import daenamoo.homepage.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Post{

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "created_at")
    private String createdAt;

    @Column(nullable = false)
    private int views;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("commentId asc") // 댓글 정렬
    private List<Comment> comments;

    /**
     * 게시글 수정
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }*/
    public void update(String title, String content) {
        if (title != null && !title.isEmpty() && content != null && !content.isEmpty()) {
            this.title = title;
            this.content = content;
        } else {
            throw new IllegalArgumentException("Title and content must not be empty");
        }
    }

}