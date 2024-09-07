package daenamoo.homepage.api;

import daenamoo.homepage.auth.userDetails.CustomUserDetails;
import daenamoo.homepage.dto.CommentDto;
import daenamoo.homepage.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(method = "POST",
            summary = "댓글 작성 API",
            description = "")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Long> save(@PathVariable Long postId,
                                     @RequestBody CommentDto.Request dto,
                                     @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String studentId = customUserDetails.getUsername();
        return ResponseEntity.ok(commentService.createComment(postId, studentId, dto));
    }

    // READ
    @Operation(method = "GET",
            summary = "댓글 조회 API",
            description = "")
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto.Response> read(@PathVariable Long postId) {
        return commentService.findAll(postId);
    }

    // UPDATE
    @Operation(method = "PUT",
            summary = "댓글 수정 API",
            description = "")
    @PutMapping({"/posts/{postId}/comments"})
    public ResponseEntity<String> update(@PathVariable Long postId,
                                         @RequestBody CommentDto.Request dto,
                                         @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String studentId = customUserDetails.getUsername();
        commentService.update(postId, studentId, dto);
        return ResponseEntity.ok(studentId);
    }

    // DELETE
    @Operation(method = "DELETE",
            summary = "댓글 삭제 API",
            description = "")
    @DeleteMapping("/posts/{postId}/comments")
    public ResponseEntity<String> delete(@PathVariable Long postId,
                                         @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String studentId = customUserDetails.getUsername();
        commentService.delete(postId, studentId);
        return ResponseEntity.ok(studentId);
    }

}
