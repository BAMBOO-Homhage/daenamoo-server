package daenamoo.homepage.dto;

import daenamoo.homepage.domain.Like;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class LikeDto {
    @Data
    @NoArgsConstructor
    public static class Request {
        private Long postId;
        private Long memberId;
    }

    @Data
    @NoArgsConstructor
    public static class Response {
        private Long likeId;
        private Long postId;
        private Long memberId;

        public Response(Long likeId, Long postId, Long memberId) {
            this.likeId = likeId;
            this.postId = postId;
            this.memberId = memberId;
        }
    }
}
