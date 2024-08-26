package daenamoo.homepage.service;

import daenamoo.homepage.dto.PostDto;
import daenamoo.homepage.domain.Member;
import daenamoo.homepage.domain.post.Post;
import daenamoo.homepage.repository.MemberRepository;
import daenamoo.homepage.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    //CREATE 글 생성
    @Transactional
    public Long createPost(PostDto.Request dto, Long memberId, Class<? extends Post> postType) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found. ID: " + memberId));
        dto.setMemberId(member.getId());
        Post post = dto.toEntity(member, postType);
        postRepository.save(post);
        return post.getPostId();
    }

    //READ 전체 글 조회
    public List<PostDto.Response> findAllPosts() {
        return postRepository.findAll().stream()
                .map(PostDto.Response::new)
                .collect(Collectors.toList());
    }

    //READ 글 한개 조회
    public PostDto.Response findOnePostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found. ID: " + id));
        return new PostDto.Response(post);
    }

    //UPDATE 글 수정
    @Transactional
    public void updatePost(Long id, PostDto.Request dto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found. ID: " + id));
        post.update(dto.getTitle(), dto.getContent());
    }

    //DELETE 글 삭제
    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Transactional
    public int updateView(Long id){
        return postRepository.updateViews(id);
    }

}