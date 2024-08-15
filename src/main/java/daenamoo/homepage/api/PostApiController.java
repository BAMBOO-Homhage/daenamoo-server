package daenamoo.homepage.api;

import daenamoo.homepage.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    //CREATE
    @PostMapping("/posts")
    public ResponseEntity save(@RequestBody PostDto.Request dto){
        Long testMemberId = 1L;
        return ResponseEntity.ok(postService.createPost(dto, testMemberId));
    }

    //READ (전체 글 조회)
    @GetMapping("/posts")
    public ResponseEntity getAllPosts(){
        return ResponseEntity.ok(postService.findAllPosts());
    }

    //READ (글 한개 조회)
    @GetMapping("/posts/{id}")
    public ResponseEntity getOnePostById(@PathVariable Long id){
        return ResponseEntity.ok(postService.findOnePostById(id));
    }

    //UPDATE
    @PutMapping("/posts/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody PostDto.Request dto){
        postService.updatePost(id, dto);
        return ResponseEntity.ok(id);
    }

    //DELETE
    @DeleteMapping("/posts/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.ok(id);
    }
}
