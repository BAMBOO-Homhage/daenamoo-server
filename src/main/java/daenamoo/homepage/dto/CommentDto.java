package daenamoo.homepage.dto;

import daenamoo.homepage.domain.Comment;
import daenamoo.homepage.domain.Member;
import daenamoo.homepage.domain.post.Post;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommentDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private Long commentId;
        private Long postId;
        private Long memberId;
        private String content;
        private LocalDateTime createdAt;

        /* Dto -> Entity */
        public Comment toEntity(Post post, Member member) {
            Comment comments = Comment.builder()
                    .commentId(commentId)
                    .post(post)
                    .member(member)
                    .content(content)
                    .createdAt(LocalDateTime.now())
                    .build();

            return comments;
        }
    }

    @RequiredArgsConstructor
    @Getter
    public static class Response {
        private Long commentId;
        private Long postId;
        private Long memberId;
        private String content;
        private LocalDateTime createdAt;

        public Response(Comment comment) {
            this.commentId = comment.getCommentId();
            this.postId = comment.getPost().getPostId();
            this.memberId = comment.getMember().getId();
            this.content = comment.getContent();
            this.createdAt = comment.getCreatedAt();
        }
    }

}
