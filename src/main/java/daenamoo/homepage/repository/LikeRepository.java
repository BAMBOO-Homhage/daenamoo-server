package daenamoo.homepage.repository;

import daenamoo.homepage.domain.Like;
import daenamoo.homepage.domain.Member;
import daenamoo.homepage.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Integer> {

    Optional<Like> findByMemberAndPost(Member member, Post post);
    void deleteByMemberAndPost(Member member, Post post);
    long countByPost(Post post);
}
