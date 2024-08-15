package daenamoo.homepage.api;

import daenamoo.homepage.domain.Member;
import daenamoo.homepage.domain.Post;
import lombok.*;

import java.time.LocalDateTime;


public class PostDto {

    // Request class
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {

        private Long postId;
        //private Member member;
        private Long memberId;
        private String title;
        private String content;
        private LocalDateTime createAt;
        private boolean isNotice;

        public Post toEntity(Member member){
            Post post = Post.builder()
                    .postId(postId)
                    .member(member)
                    .title(title)
                    .content(content)
                    .createAt(createAt)
                    .isNotice(isNotice)
                    .build();

            return post;
        }
    }

    //Response class
    @Getter
    public static class Response {
        private Long postId;
        //private Member member;
        private Long memberId;
        private String title;
        private String content;
        private LocalDateTime createAt;
        private boolean isNotice;

        public Response(Post post){
            this.postId = post.getPostId();
            //this.member = post.getMember();
            this.memberId = post.getMember().getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.createAt = post.getCreateAt();
            this.isNotice = post.isNotice();
        }
    }
}
