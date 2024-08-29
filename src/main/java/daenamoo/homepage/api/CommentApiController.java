package daenamoo.homepage.api;

import daenamoo.homepage.auth.userDetails.CustomUserDetails;
import daenamoo.homepage.dto.CommentDto;
import daenamoo.homepage.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentApiController {

    private final CommentService commentService;

    // CREATE
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Long> save(@PathVariable Long postId,
                                     @RequestBody CommentDto.Request dto,
                                     @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String studentId = customUserDetails.getUsername();
        return ResponseEntity.ok(commentService.createComment(postId, studentId, dto));
    }

    // READ
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto.Response> read(@PathVariable Long postId) {
        return commentService.findAll(postId);
    }

    // UPDATE
    @PutMapping({"/posts/{postId}/comments/{memberId}"})
    public ResponseEntity<Long> update(@PathVariable Long postId, @PathVariable Long memberId, @RequestBody CommentDto.Request dto) {
        commentService.update(postId, memberId, dto);
        return ResponseEntity.ok(memberId);
    }

    // DELETE
    @DeleteMapping("/posts/{postId}/comments/{memberId}")
    public ResponseEntity<Long> delete(@PathVariable Long postId, @PathVariable Long memberId) {
        commentService.delete(postId, memberId);
        return ResponseEntity.ok(memberId);
    }

}
