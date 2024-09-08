package daenamoo.homepage.api;

import daenamoo.homepage.auth.userDetails.CustomUserDetails;
import daenamoo.homepage.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class LikeApiController {
    private final LikeService likeService;

    @Operation(summary = "게시물 좋아요")
    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<String> likePost(@PathVariable Long postId,
                                           @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String studentId = customUserDetails.getUsername();
        Long likeId = likeService.likePost(postId, studentId);
        return ResponseEntity.ok("게시물 좋아요 성공");
    }

    @Operation(summary = "게시물 좋아요 취소")
    @DeleteMapping("/posts/{postId}/like")
    public ResponseEntity<String> unlikePost(@PathVariable Long postId,
                                             @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String studentId = customUserDetails.getUsername();
        likeService.unlikePost(postId, studentId);
        return ResponseEntity.ok("게시물 좋아요 취소 성공");
    }

    @Operation(summary = "게시물 좋아요 개수 조회")
    @GetMapping("/posts/{postId}/likes/count")
    public ResponseEntity<Long> countLikes(@PathVariable Long postId) {
        long likeCount = likeService.countLikes(postId);
        return ResponseEntity.ok(likeCount);
    }
}
