package daenamoo.homepage.repository;

import daenamoo.homepage.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
