package daenamoo.homepage.dto;

import daenamoo.homepage.domain.Member;
import daenamoo.homepage.domain.post.PostCategory;
import daenamoo.homepage.domain.post.Knowledge;
import daenamoo.homepage.domain.post.Notice;
import daenamoo.homepage.domain.post.Post;
import daenamoo.homepage.domain.post.QandA;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {

        private Long postId;
        private Long memberId;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private PostCategory category;
        private int views;

        public Post toEntity(Member member, Class<? extends Post> postType) {
            if (postType == Notice.class) {
                return Notice.builder()
                        .postId(postId)
                        .member(member)
                        .title(title)
                        .content(content)
                        .createdAt(LocalDateTime.now())
                        .category(category)
                        .views(0)
                        .build();
            } else if (postType == Knowledge.class) {
                return Knowledge.builder()
                        .postId(postId)
                        .member(member)
                        .title(title)
                        .content(content)
                        .createdAt(LocalDateTime.now())
                        .views(0)
                        .build();
            } else if (postType == QandA.class) {
                return QandA.builder()
                        .postId(postId)
                        .member(member)
                        .title(title)
                        .content(content)
                        .createdAt(LocalDateTime.now())
                        .views(0)
                        .build();
            }
            throw new IllegalArgumentException("Unsupported Post type");
        }
    }

    @Getter
    public static class Response {
        private Long postId;
        private Long memberId;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private boolean isNotice;
        private PostCategory category;
        private int views;
        private List<CommentDto.Response> comments = new ArrayList<>();

        public Response(Post post) {
            this.postId = post.getPostId();
            this.memberId = post.getMember().getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.createdAt = post.getCreatedAt();
            this.views = post.getViews();
            if (post instanceof Notice) {
                Notice notice = (Notice) post;
                this.category = notice.getCategory();
                this.comments = post.getComments().stream().map(CommentDto.Response::new).collect(Collectors.toList());
            }
        }
    }
}