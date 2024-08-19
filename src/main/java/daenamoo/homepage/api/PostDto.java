package daenamoo.homepage.api;

import daenamoo.homepage.domain.Member;
import daenamoo.homepage.domain.Post;
import daenamoo.homepage.domain.PostCategory;
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
        private LocalDateTime createdAt;
        private boolean isNotice;
        private PostCategory category;

        public Post toEntity(Member member){
            Post post = Post.builder()
                    .postId(postId)
                    .member(member)
                    .title(title)
                    .content(content)
                    .createdAt(createdAt)
                    .isNotice(isNotice)
                    .category(category)
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
        private LocalDateTime createdAt;
        private boolean isNotice;
        private PostCategory category;

        public Response(Post post){
            this.postId = post.getPostId();
            //this.member = post.getMember();
            this.memberId = post.getMember().getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.createdAt = post.getCreatedAt();
            this.isNotice = post.isNotice();
            this.category = post.getCategory();
        }
    }
}
