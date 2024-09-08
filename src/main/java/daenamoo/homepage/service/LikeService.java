package daenamoo.homepage.service;

import daenamoo.homepage.domain.Like;
import daenamoo.homepage.domain.Member;
import daenamoo.homepage.domain.post.Post;
import daenamoo.homepage.repository.LikeRepository;
import daenamoo.homepage.repository.MemberRepository;
import daenamoo.homepage.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long likePost(Long postId, String studentId){
        Member member = memberRepository.findByStudentId(studentId).orElseThrow(() ->
                new IllegalArgumentException("Invalid student ID"));
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("Invalid post ID"));

        if (likeRepository.findByMemberAndPost(member, post).isPresent()) {
            throw new IllegalStateException("Already liked this post");
        }

        Like like = Like.builder()
                .member(member)
                .post(post)
                .build();

        likeRepository.save(like);
        return like.getLikeId();
    }

    @Transactional
    public void unlikePost(Long postId, String studentId) {
        Member member = memberRepository.findByStudentId(studentId).orElseThrow(() ->
                new IllegalArgumentException("Invalid student ID"));
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("Invalid post ID"));

        Like like = likeRepository.findByMemberAndPost(member, post).orElseThrow(() ->
                new IllegalArgumentException("Like does not exist"));

        likeRepository.deleteByMemberAndPost(member, post);
    }

    @Transactional(readOnly = true)
    public long countLikes(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("Invalid post ID"));
        return likeRepository.countByPost(post);
    }



}
