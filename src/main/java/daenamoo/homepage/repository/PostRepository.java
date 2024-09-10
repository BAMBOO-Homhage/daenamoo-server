package daenamoo.homepage.repository;

import daenamoo.homepage.domain.post.Knowledge;
import daenamoo.homepage.domain.post.Notice;
import daenamoo.homepage.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {
    @Modifying
    @Transactional
    @Query("update Post p set p.views = p.views + 1 where p.postId = :id")
    int updateViews(Long id);

    Page<Post> findByTitleContaining(String keyword, Pageable pageable);

    @Query("select p from Notice p")
    List<Notice> findAllNotices();

    @Query("select p from Knowledge p")
    List<Knowledge> findAllKnowledge();
}