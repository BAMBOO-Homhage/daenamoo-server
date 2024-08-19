package daenamoo.homepage.dto.response;

import daenamoo.homepage.domain.MemberStudy;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberStudyResponseDto {
    private String name;
    private int oCount;
    private int xCount;

    public MemberStudyResponseDto(MemberStudy memberStudy) {
        this.name = memberStudy.getMember().getName();
        this.oCount = memberStudy.getOCount();
        this.xCount = memberStudy.getXCount();
    }
}
