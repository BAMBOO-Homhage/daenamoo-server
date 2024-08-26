package daenamoo.homepage.dto.request;

import daenamoo.homepage.domain.Inventory;
import daenamoo.homepage.domain.Member;
import daenamoo.homepage.domain.Study;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateInventoryRequestDto {

    private Long memberId;
    private Long studyId;
    private int week;
    private String content;

    public Inventory toEntity(Member member, Study study) {
        return Inventory.builder()
                .member(member)
                .study(study)
                .week(week)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .content(content)
                .build();
    }
}
