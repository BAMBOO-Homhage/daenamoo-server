package daenamoo.homepage.domain;

import daenamoo.homepage.dto.request.CreateInventoryRequestDto;
import daenamoo.homepage.dto.request.UpdateInventoryRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Inventory {

    @Id @GeneratedValue
    @Column(name = "inventory_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    private int week;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column(columnDefinition = "TEXT")
    private String content;

    // 수정 메서드
    public void updateInventory(UpdateInventoryRequestDto updateInventoryRequestDto) {
        this.week = updateInventoryRequestDto.getWeek();
        this.content = updateInventoryRequestDto.getContent();
        this.updatedAt = LocalDateTime.now();
    }
}
