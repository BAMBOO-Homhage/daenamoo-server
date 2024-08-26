package daenamoo.homepage.service;

import daenamoo.homepage.domain.Inventory;
import daenamoo.homepage.domain.Member;
import daenamoo.homepage.domain.Study;
import daenamoo.homepage.dto.request.CreateInventoryRequestDto;
import daenamoo.homepage.dto.request.UpdateInventoryRequestDto;
import daenamoo.homepage.repository.InventoryRepository;
import daenamoo.homepage.repository.MemberRepository;
import daenamoo.homepage.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;

    // 스터디 정리본 생성
    @Transactional
    public Long createInventory(CreateInventoryRequestDto createInventoryDto) {

        Member member = memberRepository.findById(createInventoryDto.getMemberId()).get();
        Study study = studyRepository.findById(createInventoryDto.getStudyId()).get();
        
        Inventory inventory = createInventoryDto.toEntity(member, study);
        inventoryRepository.save(inventory);
        
        return inventory.getId();
    }
    
    // 스터디 정리본 목록 조회
    public List<Inventory> findInventories() {
        return inventoryRepository.findAll();
    }
    
    // 스터디 정리본 조회
    public Inventory findInventory(Long inventoryId) {
        return inventoryRepository.findById(inventoryId).get();
    }

    // 특정 스터디의 스터디 정리본 조회
    public List<Inventory> findInventoriesInStudy(Long studyId) {
        return inventoryRepository.findByStudyId(studyId);
    }

    // 특정 멤버의 스터디 정리본 조회
    public List<Inventory> findInventoriesInMember(Long memberId) {
        return inventoryRepository.findByMemberId(memberId);
    }

    // 특정 스터디에서 특정 멤버의 스터디 정리본 조회
    public List<Inventory> findInventoriesInStudyByMember(Long studyId, Long memberId) {
        return inventoryRepository.findByStudyIdAndMemberId(studyId, memberId);
    }

    // 스터디 정리본 수정
    @Transactional
    public Long updateInventory(Long inventoryId, UpdateInventoryRequestDto updateInventoryRequestDto) {

        Inventory inventory = inventoryRepository.findById(inventoryId).get();
        inventory.updateInventory(updateInventoryRequestDto);

        return inventory.getId();
    }

    // 스터디 정리본 삭제
    @Transactional
    public Long deleteInventory(Long inventoryId) {

        Inventory inventory = inventoryRepository.findById(inventoryId).get();
        inventoryRepository.delete(inventory);

        return inventory.getId();
    }
}
