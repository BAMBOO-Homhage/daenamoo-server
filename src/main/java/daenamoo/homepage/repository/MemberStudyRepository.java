package daenamoo.homepage.repository;

import daenamoo.homepage.domain.MemberStudy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberStudyRepository extends JpaRepository<MemberStudy, Long> {
}
