package daenamoo.homepage.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Builder
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private LocalDateTime createAt;
    private boolean isNotice;

    // 게시글 수정
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
