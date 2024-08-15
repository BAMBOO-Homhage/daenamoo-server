package daenamoo.homepage.service;

import daenamoo.homepage.api.PostDto;
import daenamoo.homepage.domain.Member;
import daenamoo.homepage.domain.Post;
import daenamoo.homepage.repository.MemberRepository;
import daenamoo.homepage.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    //CREATE
    @Transactional
    public Long createPost(PostDto.Request dto, Long memberId){
        Member member = memberRepository.findById(memberId).get();
        dto.setMemberId(memberId);
        Post post = dto.toEntity(member);
        postRepository.save(post);

        return post.getPostId();
    }

    //READ (모든 글 조회)
    public List<PostDto.Response> findAllPosts(){
        try {
            List<Post> postList = postRepository.findAll();
            List<PostDto.Response> responseList = new ArrayList<>();
            for (Post post : postList){
                responseList.add( new PostDto.Response(post) );
            }
            return responseList;
        } catch (Exception e){
            //오류 로그를 찍을까..?
        }
        return null;
    }

    //READ (글 한개 조회)
    public PostDto.Response findOnePostById(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다. id:" + id));
         return new PostDto.Response(post);
    }

    //UPDATE
    //변경감지(dirty checking)
    @Transactional
    public void updatePost(Long id, PostDto.Request dto){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다. id:" + id));
        post.update(dto.getTitle(), dto.getContent());
    }

    //DELETE
    @Transactional
    public void deletePost(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다. id:" + id));
        postRepository.delete(post);
    }



}
