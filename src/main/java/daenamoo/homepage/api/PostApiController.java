package daenamoo.homepage.api;

import daenamoo.homepage.dto.PostDto;
import daenamoo.homepage.service.PostService;
import daenamoo.homepage.domain.post.Knowledge;
import daenamoo.homepage.domain.post.Notice;
import daenamoo.homepage.domain.post.QandA;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
class PostApiController {

    private final PostService postService;

    @Operation(method = "POST",
            summary = "공지사항게시판 작성 API",
            description = "공지사항게시판 작성 API입니다.")
    @PostMapping("/notices")
    public ResponseEntity<String> createNotice(@RequestBody PostDto.Request dto) {
        Long testMemberId = 1L;
        postService.createPost(dto, testMemberId, Notice.class);
        return ResponseEntity.status(HttpStatus.CREATED).body("공지사항게시판 작성에 성공했습니다.");
    }

    @Operation(method = "POST",
            summary = "지식공유게시판 작성 API",
            description = "지식공유게시판 작성 API입니다.")
    @PostMapping("/knowledge")
    public ResponseEntity<String> createKnowledge(@RequestBody PostDto.Request dto) {
        Long testMemberId = 1L;
        postService.createPost(dto, testMemberId, Knowledge.class);
        return ResponseEntity.status(HttpStatus.CREATED).body("지식공유게시판 작성에 성공했습니다.");
    }

    @Operation(method = "POST",
            summary = "질문답변게시판 작성 API",
            description = "질문답변게시판 작성 API입니다.")
    @PostMapping("/QandA")
    public ResponseEntity<String> createQandA(@RequestBody PostDto.Request dto) {
        Long testMemberId = 1L;
        postService.createPost(dto, testMemberId, QandA.class);
        return ResponseEntity.status(HttpStatus.CREATED).body("질문답변게시판 작성에 성공했습니다.");
    }

    @Operation(method = "GET",
            summary = "전체글 조회 API",
            description = "")
    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok(postService.findAllPosts());
    }

    @Operation(method = "GET",
            summary = "게시글 한개 조회 API",
            description = "")
    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getOnePostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findOnePostById(id));
    }

    @Operation(method = "PUT",
            summary = "게시글 업데이트 API",
            description = "")
    @PutMapping("/posts/{id}")
    public ResponseEntity<Long> updatePost(@PathVariable Long id, @RequestBody PostDto.Request dto) {
        postService.updatePost(id, dto);
        return ResponseEntity.ok(id);
    }

    @Operation(method = "DELETE",
            summary = "게시글 삭제 API",
            description = "")
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Long> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(id);
    }
}