package daenamoo.homepage.service;

import daenamoo.homepage.domain.Comment;
import daenamoo.homepage.domain.Member;
import daenamoo.homepage.domain.post.Post;
import daenamoo.homepage.dto.CommentDto;
import daenamoo.homepage.repository.CommentRepository;
import daenamoo.homepage.repository.MemberRepository;
import daenamoo.homepage.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    //CREATE 댓글 생성
    @Transactional
    public Long createComment(Long memberId, Long postId, CommentDto.Request dto) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 회원이 존재하지 않습니다. " + memberId));
        ;
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시물이 존재하지 않습니다. " + postId));

        dto.setMemberId(memberId);
        dto.setPostId(postId);

        Comment comment = dto.toEntity(post, member);
        commentRepository.save(comment);

        return comment.getCommentId();
    }

    //READ
    @Transactional(readOnly = true)
    public List<CommentDto.Response> findAll(Long id) {
        Post posts = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id));
        List<Comment> comments = posts.getComments();
        return comments.stream().map(CommentDto.Response::new).collect(Collectors.toList());
    }

    /* UPDATE */
    @Transactional
    public void update(Long postId, Long memberId, CommentDto.Request dto) {
        Comment comment = commentRepository.findByPost_PostIdAndMemberId(postId, memberId).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + memberId));

        comment.update(dto.getContent());
    }

    /* DELETE */
    @Transactional
    public void delete(Long postId, Long memberId) {
        Comment comment = commentRepository.findByPost_PostIdAndMemberId(postId, memberId).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + memberId));

        commentRepository.delete(comment);
    }




}
