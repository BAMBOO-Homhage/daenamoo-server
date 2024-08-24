package daenamoo.homepage.repository;

import daenamoo.homepage.domain.Member;
import daenamoo.homepage.domain.Register;
import daenamoo.homepage.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisterRepository extends JpaRepository<Register, Long> {
    Optional<Register> findByMemberAndSubject(Member member, Subject subject);
}
