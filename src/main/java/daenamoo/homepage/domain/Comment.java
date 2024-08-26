package daenamoo.homepage.domain;

import daenamoo.homepage.domain.post.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // 작성자

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // 댓글 내용

    @Column(name = "created_at")
    @CreatedDate
    private String createdAt;

    //댓글 수정
    public void update(String content) {
        this.content = content;
    }
}
