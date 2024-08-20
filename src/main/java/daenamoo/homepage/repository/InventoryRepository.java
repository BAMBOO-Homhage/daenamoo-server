package daenamoo.homepage.repository;

import daenamoo.homepage.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByStudyId(Long studyId);
    List<Inventory> findByMemberId(Long memberId);
    List<Inventory> findByStudyIdAndMemberId(Long studyId, Long memberId);
}
