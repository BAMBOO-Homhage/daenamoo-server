package daenamoo.homepage.repository;

import daenamoo.homepage.domain.Register;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterRepository extends JpaRepository<Register, Long> {
}
