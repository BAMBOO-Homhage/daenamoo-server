package daenamoo.homepage.dto.response;

import daenamoo.homepage.domain.Inventory;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryResponseDto {

    private Long memberId;
    private Long studyId;
    private int week;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column(columnDefinition = "TEXT")
    private String content;

    public InventoryResponseDto(Inventory inventory) {
        this.memberId = inventory.getMember().getId();
        this.studyId = inventory.getStudy().getId();
        this.week = inventory.getWeek();
        this.createdAt = inventory.getCreatedAt();
        this.updatedAt = inventory.getUpdatedAt();
        this.content = inventory.getContent();
    }
}
