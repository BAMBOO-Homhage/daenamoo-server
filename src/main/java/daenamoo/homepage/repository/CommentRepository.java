package daenamoo.homepage.repository;

import daenamoo.homepage.domain.Comment;
import daenamoo.homepage.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    /* 게시글 댓글 목록 가져오기 */
    List<Comment> getCommentByPostOrderByCommentId(Post post);

    Optional<Comment> findByPost_PostIdAndCommentId(Long postId, Long commentId);
}