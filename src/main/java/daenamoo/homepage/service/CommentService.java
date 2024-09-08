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
    public Long createComment(Long postId, String studentId, CommentDto.Request dto) {
        Member member = memberRepository.findByStudentId(studentId).get();
        Post post = postRepository.findById(postId).get();

        dto.setMemberId(member.getId());
        dto.setPostId(postId);

        Comment comment = dto.toEntity(post, member);
        commentRepository.save(comment);

        return comment.getCommentId();
    }

    //READ
    @Transactional(readOnly = true)
    public List<CommentDto.Response> findAll(Long id) {
        Post post = postRepository.findById(id).get();
        List<Comment> comments = post.getComments();
        return comments.stream().map(CommentDto.Response::new).collect(Collectors.toList());
    }

    /* UPDATE */
    @Transactional
    public void update(Long postId, Long commentId, CommentDto.Request dto) {
        Comment comment = commentRepository.findByPost_PostIdAndCommentId(postId, commentId).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + commentId));

        comment.update(dto.getContent());
    }

    /* DELETE */
    @Transactional
    public void delete(Long postId, Long commentId) {
        Comment comment = commentRepository.findByPost_PostIdAndCommentId(postId, commentId).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + commentId));

        commentRepository.delete(comment);
    }




}
