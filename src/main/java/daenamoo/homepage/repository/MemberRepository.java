package daenamoo.homepage.repository;

import daenamoo.homepage.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByName(String name);
    List<Member> findByStudentId(String si);
}
