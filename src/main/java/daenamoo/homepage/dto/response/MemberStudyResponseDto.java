package daenamoo.homepage.dto.response;

import daenamoo.homepage.domain.MemberStudy;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberStudyResponseDto {
    private String studyName;
    private int oCount;
    private int xCount;

    public MemberStudyResponseDto(MemberStudy memberStudy) {
        this.studyName = memberStudy.getStudy().getName();
        this.oCount = memberStudy.getOCount();
        this.xCount = memberStudy.getXCount();
    }
}
